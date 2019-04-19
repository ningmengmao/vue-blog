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

    private ArticleService articleService;

    @GetMapping("/articles/{pageNum}")
    public List<ArticleVO> getArticleByPageNum(@PathVariable("pageNum") Integer number){
        if (number < 1)
            return new ArrayList<>();
        Page<ArticleVO> articles = articleService.getArticles();
        return articles.getOnePage(number);
    }


    @Autowired
    public void setArticleService(ArticleService articleService){
        this.articleService = articleService;
    }
}
