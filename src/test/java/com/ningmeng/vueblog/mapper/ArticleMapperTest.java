package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ningmeng.vueblog.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void test(){
        Page<Article> articlePage = new Page<>();
        System.out.println(articleMapper.selectById(1));
    }

    @Test
    public void testWrapper(){
        IPage<Article> articleIPage = articleMapper.selectPage(new Page<Article>(),
                new QueryWrapper<Article>()
                        .ge("create_time", 1555519854473L)
                        .orderByDesc("update_time")
        );
        List<Article> records = articleIPage.getRecords();
        for (Article a : records)
            System.out.println(a);
    }

    @Test
    public void testDeleteAll(){
//        articleMapper.delete(null);
        Article article = new Article();
        article.setId(1);
        article.setTitle("11111");
        //MybatisPlusException: Prohibition of table update operation
//        articleMapper.update(article, new UpdateWrapper<Article>());
    }
}
