package com.ningmeng.vueblog.controller;

import com.ningmeng.vueblog.annocation.AdminAccessAnnotation;
import com.ningmeng.vueblog.entity.Timeline;
import com.ningmeng.vueblog.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/5 下午4:26
 * @Version 1.0
 */
@RequestMapping("/api/timeline")
@RestController
public class TimelineController {
    private TimelineService timelineService;

    @GetMapping("")
    public Map<String, Object> getTimeline() {
        HashMap<String, Object> map = new HashMap<>(2);
        List<Timeline> timelineList = timelineService.getAll();
        map.put("data", timelineList);
        map.put("message", "success");
        return map;
    }

    @AdminAccessAnnotation
    @PostMapping("")
    public void addTimeline(@RequestBody Timeline timeline, HttpServletResponse response, HttpServletRequest request) {
        timeline.setCreateTime(timeline.getCreateTime() / 1000);
        timelineService.insert(timeline);
        response.setStatus(200);
    }

    @AdminAccessAnnotation
    @PutMapping("/{id}")
    public void updateTimeline(@PathVariable("id") int id, @RequestBody Timeline timeline, HttpServletResponse response, HttpServletRequest request) {
        timeline.setId(id);
        timeline.setCreateTime(timeline.getCreateTime() / 1000);
        timelineService.update(timeline);
    }

    @AdminAccessAnnotation
    @DeleteMapping("/{id}")
    public void delTimeline(@PathVariable("id") int id, HttpServletResponse response, HttpServletRequest request) {
        timelineService.delete(id);
    }

    @Autowired
    public void setTimelineService(TimelineService timelineService) {
        this.timelineService = timelineService;
    }
}
