package com.ningmeng.vueblog.other;

import com.ningmeng.vueblog.entity.Article;
import org.apache.ibatis.annotations.Insert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class MyTest {

    @Test
    public void testTime(){
        LocalDateTime begin = LocalDateTime.of(2013, 2, 1, 0, 0, 0, 0);

        LocalDateTime now = LocalDateTime.now(ZoneOffset.ofHours(8));
        System.out.println(begin);
        System.out.println(begin.toEpochSecond(ZoneOffset.ofHours(7)));
    }

    @Test
    public void test2(){
        Instant now = Instant.now();
        System.out.println(now.getEpochSecond() + "-" + now.getNano());
        System.out.println(now.toEpochMilli());
    }
}
