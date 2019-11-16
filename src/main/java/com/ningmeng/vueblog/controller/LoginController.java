package com.ningmeng.vueblog.controller;

import com.ningmeng.vueblog.config.Audience;
import com.ningmeng.vueblog.dto.GithubTokenDTO;
import com.ningmeng.vueblog.dto.GithubUserDTO;
import com.ningmeng.vueblog.entity.User;
import com.ningmeng.vueblog.exception.GithubTimeOutException;
import com.ningmeng.vueblog.provider.GithubAccessProvider;
import com.ningmeng.vueblog.provider.JwtProvider;
import com.ningmeng.vueblog.service.UserService;
import com.ningmeng.vueblog.util.MyJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/20 上午3:08
 * @Version 1.0
 */
@Slf4j
@RestController
public class LoginController {

    private Audience audience;

    private JwtProvider jwtProvider;

    private GithubAccessProvider githubAccessProvider;

    private RedisTemplate redisTemplate;

    private UserService userService;

    @Value("${github.client_id}")
    private String clientID;

    @Value("${github.client_secret}")
    private String clientSecret;

    @Value("${github.redirect_url}")
    private String redirectURL;

    @Value("${github.github_Auth_url}")
    private String githubAuthUrl;

    @Value("${index-path}")
    private String indexPath;


    /*
     从这里做重定向到github授权,避免暴露api
     */

    @GetMapping("/api/githubLogin")
    public Map<String, Object> githubLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorization = request.getHeader("Authorization");
//        response.setHeader("Access-Control-Allow-Origin","*");
//        response.setHeader("Access-Control-Allow-Methods","GET");
//        response.setHeader("Access-Control-Allow-Headers","Access-Control");
//        response.setHeader("Allow","*");

        // 重定向到github授权系统
        if (null == authorization || "".equals(authorization)) {
            return MyJson.toJson(302, "重定向到github登录页面", githubAuthUrl);
        } else {
            String token = authorization.replace("Bearer ", "");
            ValueOperations ops = redisTemplate.opsForValue();
            User user = (User) ops.get("token-" + token);
            if (user == null) {
                response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                return MyJson.toJson(302, "token不存在或过期,请再次登录", githubAuthUrl);
            } else {
                log.info(Instant.now().toString() + " : " + user.getId() + "---" + user.getUsername() + "---登录");
                return MyJson.toJson(200, "登录成功", null);
            }
        }
    }

    /**
     * github回调接口, github返回code, state参数传入此方法
     *
     * @param githubTokenDTO
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/api/githubCallback")
    public void githubLoginCallBack(GithubTokenDTO githubTokenDTO, HttpServletRequest request, HttpServletResponse response) throws GithubTimeOutException {
        response.setHeader("Access-Control-Allow-Origin", "https://github.com");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Access-Control");
        response.setHeader("Allow", "*");
        githubTokenDTO.setClient_id(clientID);
        githubTokenDTO.setClient_secret(clientSecret);
        githubTokenDTO.setRedirect_url(redirectURL);
        String accessToken = githubAccessProvider.getAccessToken(githubTokenDTO);
        GithubUserDTO githubUserDTO = githubAccessProvider.getGithubUserDTO(accessToken);

        HashMap<String, Object> map = new HashMap<>(8);
        ValueOperations ops = redisTemplate.opsForValue();
        String token = "";
        if (!Objects.isNull(githubUserDTO)) {

            // token放在redis中
            // 数据库中是否存在此用户
            User userByAccountId = userService.findUserByAccountId(githubUserDTO.getId());
            // 用户存在, name和accountId匹配, 验证成功
            if (Objects.nonNull(userByAccountId) && userByAccountId.getUsername().equals(githubUserDTO.getName())) {
                token = JwtProvider.createJWT(userByAccountId.getId(), userByAccountId.getUsername(), null, audience);
                // token 1800s过期
                ops.set("token-" + token, userByAccountId, 1800, TimeUnit.SECONDS);
                if (userByAccountId.getAccountId().equals(32759211L) && "https://github.com/ningmengmao".equals(userByAccountId.getUserUrl()) && "Eric".equals(userByAccountId.getUsername())) {
                    Cookie cookie = new Cookie("admin", Base64.getEncoder().encodeToString("我是管理员".getBytes(StandardCharsets.UTF_8)));
                    cookie.setPath("/");
                    cookie.setMaxAge(1800);
                    response.addCookie(cookie);
                }
            }

            if (Objects.isNull(userByAccountId)) {
                User user = new User();
                user.setUsername(githubUserDTO.getName());
                user.setAccountId(githubUserDTO.getId());
                user.setAvatarUrl(githubUserDTO.getAvatar_url());
                user.setBio(githubUserDTO.getBio());
                user.setCreateTime(Instant.now().toEpochMilli());
                user.setUserUrl(githubUserDTO.getHtml_url());
                // 将用户加入到db中
                int id = userService.addUser(user);
                token = JwtProvider.createJWT(id, user.getUsername(), null, audience);
                ops.set("token-" + token, user, 1800, TimeUnit.SECONDS);
                if (user.getAccountId().equals(32759211L) && "https://github.com/ningmengmao".equals(user.getUserUrl()) && "Eric".equals(user.getUsername())) {
                    Cookie cookie = new Cookie("admin", Base64.getEncoder().encodeToString("我是管理员".getBytes(StandardCharsets.UTF_8)));
                    cookie.setPath("/");
                    cookie.setMaxAge(1800);
                    response.addCookie(cookie);
                }
            }
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(1800);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        response.setStatus(302);
        response.setHeader("location", indexPath);
    }

    @GetMapping("/api/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    redisTemplate.expire("token-" + cookie.getValue(), 0, TimeUnit.SECONDS);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        response.setStatus(200);
    }

    /**
     * http://openapi.baidu.com/oauth/2.0/authorize?client_id=Okjx7mzOlwiD8qM6ELiwK1Kq&response_type=code&redirect_uri=http://localhost:23333/baiduCallback&scope=basic&state=1
     *
     * @param code
     * @param state
     */
    @GetMapping("/api/baiduCallback")
    public void baiduLoginCallBack(String code, String state) {
        System.out.println(code + "  " + state);
    }


    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setGithubAccessProvider(GithubAccessProvider githubAccessProvider) {
        this.githubAccessProvider = githubAccessProvider;
    }
}
