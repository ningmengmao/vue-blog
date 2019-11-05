package com.ningmeng.vueblog.other;

import com.ningmeng.vueblog.entity.Article;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.ibatis.annotations.Insert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void test2() throws IOException {
        String url = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String resultString = response.body().string();
        String regex = ".*(?<=url\":\")(.*?)(?=\",\"urlbase).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(resultString);
        String imgUrl = null;
        if (matcher.matches()) {
            imgUrl = matcher.group(1);
            Request imgRequest = new Request.Builder()
                    .url("https://cn.bing.com/" + imgUrl)
                    .build();
            byte[] image = Objects.requireNonNull(client.newCall(imgRequest).execute().body()).bytes();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File("/home/ningmengmao/Pictures/" + UUID.randomUUID() + ".jpg")));
            bufferedOutputStream.write(image);
            bufferedOutputStream.close();
        }
    }
}
