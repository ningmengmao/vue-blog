package com.ningmeng.vueblog.other;

import com.ningmeng.vueblog.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MyTest {

    @Test
    public void test() throws NoSuchFieldException, IllegalAccessException {
        ArrayList<Article> articles = new ArrayList<>();
        Class<? extends ArrayList> aClass = articles.getClass();
        System.out.println(Iterable.class.isAssignableFrom(aClass));
    }
}
