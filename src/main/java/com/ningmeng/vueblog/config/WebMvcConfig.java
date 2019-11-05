package com.ningmeng.vueblog.config;

import com.ningmeng.vueblog.interceptor.GlobalInterceptor;
import com.ningmeng.vueblog.interceptor.NormalUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/23 上午9:46
 * @Version 1.0
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private NormalUserInterceptor normalUserInterceptor;

    @Autowired
    private GlobalInterceptor globalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 需要admin权限
        String[] urlPath = new String[]{
                "/api/articles/article/*",
                "/api/articles/article",
                "/api/comments/*",
                "/api/file/img",
                "/api/file/img/*",
                "/api/link",
                "/api/link/*",
                "/api/tags",

        };

        // 需要login 普通用户只需要登录获取评论权限
        String[] path = new String[]{
                "/api/comments/**"
        };

        // 拦截发表评论
        registry.addInterceptor(normalUserInterceptor).addPathPatterns(path);

        // 刷新cookie AND token
        registry.addInterceptor(globalInterceptor).addPathPatterns("/**");
        /*
        @PutMapping("/article/{id}") updateArticle
        @DeleteMapping("/article/{id}") deleteArticle
        @PostMapping("/article") addArticle

        api/comments
        @DeleteMapping("/{id}") delete
        @PostMapping("/{id}") addComment

        @RequestMapping("api/file")
        @PostMapping("/img") uploadImage
        @DeleteMapping("/img/{fileName}") delImage

        "api/links"
        @PostMapping("") addLink
        @DeleteMapping("/{id}") delete

        @RequestMapping("api/tags")
            @PostMapping("") addTag
        @DeleteMapping("") delTag

         */
    }
}
