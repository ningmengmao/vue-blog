package com.ningmeng.vueblog.controller;

import com.ningmeng.vueblog.vo.ArticleVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/a")
@RestController
public class ArticleController {


    @GetMapping("/articles/{pageNum}")
    public List<ArticleVO> getArticleByPageNum(@PathVariable("pageNum") Integer number){
        //todo
        return null;
    }



}
