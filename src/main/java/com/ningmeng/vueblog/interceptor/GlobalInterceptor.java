package com.ningmeng.vueblog.interceptor;

import com.ningmeng.vueblog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/3 上午11:39
 * @Version 1.0
 */
@Slf4j
@Component
public class GlobalInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    @Autowired
    public GlobalInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 刷新cookie And token
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = (User) redisTemplate.opsForValue().get("token-" + token);
                    if (Objects.nonNull(user)) {
                        redisTemplate.expire("token-" + token, 1800L, TimeUnit.SECONDS);
                        cookie.setMaxAge(60 * 30);
                    }
                }
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return true;
    }
}
