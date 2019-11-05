package com.ningmeng.vueblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.vo.ArticleVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void test(){
        IPage<Article> articlesByPageNumber = articleService.getArticlesByPageNumber(2);
        List<Article> records = articlesByPageNumber.getRecords();
        System.out.println(1);
    }

    @Test
    public void test1(){
        Article byId = articleService.findById(23);
        ArticleVO articleVO = new ArticleVO(byId, null);
        System.out.println(1);
    }


}
