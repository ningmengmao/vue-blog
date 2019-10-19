package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.Tag;
import com.ningmeng.vueblog.vo.TagVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void test(){
        Tag tag = tagService.getByName("测试1");
        System.out.println(new TagVO(tag, 1));
    }
}
