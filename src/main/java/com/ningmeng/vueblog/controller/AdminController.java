package com.ningmeng.vueblog.controller;

import com.ningmeng.vueblog.util.MyJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
}
