package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkServiceTest {

    @Autowired
    private LinkService linkService;

    @Test
    public void test(){
        Link link = new Link();
        link.setTitle("google");
        link.setDescription("google");
        link.setUrl("https://www.google.com");
        linkService.insert(link);
    }
}
