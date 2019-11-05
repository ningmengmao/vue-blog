package com.ningmeng.vueblog.interceptor;

import com.ningmeng.vueblog.entity.User;
import com.ningmeng.vueblog.util.MyJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/23 上午9:06
 * @Version 1.0
 */

@Slf4j
@Component
public class NormalUserInterceptor implements HandlerInterceptor {

    private RedisTemplate redisTemplate;

    @Autowired
    public NormalUserInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public NormalUserInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        boolean b = method.hasMethodAnnotation(PostMapping.class);
        if (b) {
            String authorization = request.getHeader("Authorization");

            if (null == authorization || "".equals(authorization)) {
                response.getWriter().println(MyJson.toJson(403, "请登录后再使用评论功能", null));
                return false;
            } else {
                String token = authorization.replace("Bearer ", "");
                ValueOperations ops = redisTemplate.opsForValue();
                User user = (User) ops.get("token-" + token);
                if (user == null) {
                    response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                    response.getWriter().println(MyJson.toJson(403, "token不存在或过期,请再次登录", null));
                    return false;
                } else {
                    log.info(Instant.now().toString() + " : " + user.getId() + "---" + user.getUsername() + "发表评论");
                    return true;
                }
            }
        }
        return true;
    }

}
