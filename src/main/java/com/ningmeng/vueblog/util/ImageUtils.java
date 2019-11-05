package com.ningmeng.vueblog.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/28 下午2:40
 * @Version 1.0
 */
@Component
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "background")
public class ImageUtils {

    private String url;
    private List<String> location = new ArrayList<>();

    public ImageUtils() {
    }

    /**
     * 每小时在backgroundImageLocation下生成year-month-day.jpg的图片
     *
     * @throws IOException
     */
    @Scheduled(cron = "0 5 0/12 * * ?")
    public void downloadBackgroundImage() throws IOException {
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
            LocalDate now = LocalDate.now(ZoneOffset.ofHours(8));

            for (String path : location) {
                String fileName = path + now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() + ".jpg";
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(
                        new File(fileName)));
                bufferedOutputStream.write(image);
                bufferedOutputStream.close();
                log.info("更新背景图片: " + fileName);
            }
        }
    }

}
