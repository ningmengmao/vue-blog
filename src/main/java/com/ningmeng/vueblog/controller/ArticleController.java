package com.ningmeng.vueblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.service.ArticleService;
import com.ningmeng.vueblog.util.MyJson;
import com.ningmeng.vueblog.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("api/article")
@RestController
public class ArticleController {

    private ArticleService articleService;

    @GetMapping("/{pageNum}")
    public Map<String, Object> getArticleByPageNum(@PathVariable("pageNum") Integer number){
        if (number < 1)
            return MyJson.toJson(MyJson.BAD_REQUEST, "页码数必须大于0", new ArrayList<ArticleVO>());
        IPage<Article> articles = articleService.getArticlesByPageNumber(number);
        long pages = articles.getPages();
        if (number > pages)
            return MyJson.toJson(MyJson.BAD_REQUEST, "页码数超出", new ArrayList<ArticleVO>());
        ArrayList<ArticleVO> articleVOS = new ArrayList<>();
        for (Article a : articles.getRecords())
            articleVOS.add(new ArticleVO(a));
        return MyJson.toJson(MyJson.SUCCESS, "success", articleVOS);
    }

    /**
     * 获取分页总数
     * @return
     */
    @GetMapping("/pages")
    public Map<String, Object> getPages(){
        long pages = articleService.getArticlesByPageNumber(1).getPages();
        return MyJson.toJson(MyJson.SUCCESS, "success", pages);
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }
}
