package com.ningmeng.vueblog.controller;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.service.ArticleService;
import com.ningmeng.vueblog.vo.ArticleVO;
import com.ningmeng.vueblog.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
