package com.ningmeng.vueblog.other;

import com.ningmeng.vueblog.config.Audience;
import com.ningmeng.vueblog.provider.JwtProvider;
import com.ningmeng.vueblog.util.ImageUtils;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.dc.pr.PRError;

import java.io.IOException;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/21 下午6:57
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenTest {

    @Autowired
    private Audience audience;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    public void test() {
        String jwt = JwtProvider.createJWT(1, "user123", "no", audience);
        System.out.println(jwt);
    }

//    @Test
//    public void test1(){
//        Claims claims = JwtProvider.parseJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoibm8iLCJqdGkiOiJNUT09Iiwic3ViIjoidnVlLWJsb2ciLCJpc3MiOiJ2dWUtYmxvZyIsImlhdCI6MTU3MTY1NjUwNSwiYXVkIjoidXNlcjEyMyIsImV4cCI6MTU3MTY2MzcwNX0.wNntlrNRrRp4NK_9sqCiKk3ihRX8czbjabqHtpY_0BlokWiCdyYA226aVZt7yb828Nj8ec6v4DYX4faQMhHpGA", audience.getBase64Secret());
//        System.out.println(claims);
//    }
//
//    @Test
//    public void test2() throws IOException {
//        imageUtils.getBackgroundImage();
//    }
}
