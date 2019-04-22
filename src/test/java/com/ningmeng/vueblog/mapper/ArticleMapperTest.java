package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Tag;
import com.ningmeng.vueblog.other.MyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleMapperTest {

    @Autowired
    private TagMapper tagMapper;

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
    @Test
    public void test11(){
        List<Article> articles = articleMapper.selectByTagId(4);
        for (Article a : articles)
            System.out.println(a);
    }

    @Test
    public void test1111(){
        Article article = articleMapper.selectByArticleId(23);
        System.out.println(article);
    }

    @Test
    public void ttttt(){
        List<Tag> tags = tagMapper.selectByArticleId(23);
        System.out.println(Arrays.toString(tags.toArray()));
    }

}
