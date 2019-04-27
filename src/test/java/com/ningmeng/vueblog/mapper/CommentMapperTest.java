package com.ningmeng.vueblog.mapper;

import com.ningmeng.vueblog.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void test(){
        List<Comment> comments = commentMapper.selectByArticleId(23);
        System.out.println(1);
    }

}
