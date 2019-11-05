package com.ningmeng.vueblog;

import com.ningmeng.vueblog.util.ImageUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VueBlogApplicationTests {

    @Autowired
    private ImageUtils imageUtils;
    @Test
    public void contextLoads() throws InterruptedException, IOException {
        imageUtils.downloadBackgroundImage();
    }

}
