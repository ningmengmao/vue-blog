package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.vo.ArticleVO;
import com.ningmeng.vueblog.vo.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {


    @Autowired
    private ArticleService articleService;

    @Transactional
    @Test
    public void testPage(){
        Page<ArticleVO> articles = articleService.getArticles();

        for (ArticleVO a : articles.getData())
            System.out.println(a);
    }

    @Test
    public void testSave() throws InterruptedException {
        ArrayList<ArticleVO> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            articles.add(new ArticleVO( new Article("tttttt", "absssss", "cccccc", 0, new Date().getTime(), false)));
            Thread.sleep(100);
        }
        articleService.update(articles);
    }

}
