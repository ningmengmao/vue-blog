package com.ningmeng.vueblog.other;

import com.ningmeng.vueblog.util.ImageUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/6 下午11:41
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageUtilTest {
    @Autowired
    private ImageUtils imageUtils;

    @Test
    public void test() throws IOException {
        imageUtils.downloadBackgroundImage();
    }
}
