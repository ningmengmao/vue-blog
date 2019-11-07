package com.ningmeng.vueblog.controller;

import com.ningmeng.vueblog.annocation.AdminAccessAnnotation;
import com.ningmeng.vueblog.exception.GithubTimeOutException;
import com.ningmeng.vueblog.util.MyJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @GetMapping("/login")
    public Map<String,Object> login(HttpSession session){
        System.out.println("login");
        session.setAttribute("access_token", true);
        return MyJson.toJson(MyJson.SUCCESS, "success", new ArrayList<>());
    }

    @AdminAccessAnnotation
    @GetMapping("/test")
    public Map<String, Object> test(HttpServletResponse response, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("test", "hello world");
        return map;
    }

    @GetMapping("/test1")
    public void test1() throws GithubTimeOutException {
        throw new GithubTimeOutException("test");
    }
}
