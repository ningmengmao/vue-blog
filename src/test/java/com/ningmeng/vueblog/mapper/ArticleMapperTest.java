package com.ningmeng.vueblog.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleMapperTest {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void test1(){
//        Article article = articleMapper.selectByArticleId(23);
//        System.out.println(article);
        System.out.println("!23");
    }

//    @Test
//    public void ttt(){
//        IPage<Article> update_time = articleMapper.selectPage(new Page<Article>(), new QueryWrapper<Article>().orderByDesc("update_time"));
//        System.out.println(1);
//    }

//    @Test
//    public void tt(){
//        Date begin = new Date( 119, 3 ,1);
//        Date end = new Date(119, 5, 1);
//
//        System.out.println(begin.getTime());
//        IPage<Article> byCreateTimeBetween = articleMapper.findByCreateTimeBetween(new Page<ArticleMapper>(1, 8), begin.getTime() , end.getTime() );
//        System.out.println(1);
//    }
}
