package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.Timeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/5 下午5:19
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TimelineServiceTest {

    @Autowired
    public TimelineService timelineService;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            Timeline timeline = new Timeline();
            LocalDateTime date = LocalDateTime.of(2019, 11, 5, 17, i);
            timeline.setTitle("title-" + i);
            timeline.setContent("content-" + i);
            timeline.setCreateTime(date.toEpochSecond(ZoneOffset.ofHours(8)));
            timelineService.insert(timeline);
        }

    }

    @Test
    public void testGetAll() {
        List<Timeline> all = timelineService.getAll();
        for (Timeline timeline : all) {
            System.out.println(timeline);

        }
    }
}
