package com.ningmeng.vueblog.mapper;

import com.ningmeng.vueblog.entity.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagMapperTest {

    @Autowired
    private TagMapper tagMapper;

    @Test
    public void test(){
        List<Tag> tags = tagMapper.selectByArticleId(23);
        System.out.println(1);
    }
}
