package com.ningmeng.vueblog.aop;

import com.ningmeng.vueblog.entity.User;
import com.ningmeng.vueblog.exception.NullAuthHearException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/6 下午6:30
 * @Version 1.0
 */
@Component
@Aspect
@Slf4j
public class AdminPrivilegeAop {

    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.ningmeng.vueblog.annocation.AdminAccessAnnotation)")
    public void pointCut() {
    }


    @Around(value = "pointCut()")
    public Object validateAdminPrivilege(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        ArrayList<Object> inputArg = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            } else if (arg instanceof HttpServletResponse) {
                response = (HttpServletResponse) arg;
            } else {
                inputArg.add(arg);
            }
        }

        String authorization = null;
        try {
            authorization = request.getHeader("Authorization");
        } catch (NullPointerException e) {
            throw new NullAuthHearException("未携带token");
        }
        if (authorization != null) {
            String token = authorization.replace("Bearer ", "");
            User user = (User) redisTemplate.opsForValue().get("token-" + token);
            if (Objects.nonNull(user)) {
                if (user.getAccountId().equals(32759211L) && "https://github.com/ningmengmao".equals(user.getUserUrl())) {
                    String signature = joinPoint.getSignature().getName();
                    log.info("执行: " + joinPoint.getTarget().getClass().getCanonicalName() + "." + signature + " ,参数: " + inputArg.toString());
                    return joinPoint.proceed(args);
                } else {
                    response.sendError(403, "无权访问");
                }
            } else {
                response.sendError(401, "token过期或不存在");
            }
        } else {
            response.sendError(401, "Authorization为空");
        }
        return new HashMap<>(1);

    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
