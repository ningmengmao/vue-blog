package com.ningmeng.vueblog.service.impl;

import com.ningmeng.vueblog.entity.Timeline;
import com.ningmeng.vueblog.mapper.TimelineMapper;
import com.ningmeng.vueblog.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/5 下午5:02
 * @Version 1.0
 */

@CacheConfig(cacheNames = "timeline")
@Service
public class TimelineServiceImpl implements TimelineService {

    private TimelineMapper timelineMapper;

    @Cacheable(key = "#root.methodName")
    @Override
    public List<Timeline> getAll() {
        return timelineMapper.getAllTimeline();
    }

    @CacheEvict(cacheNames = "timeline", allEntries = true)
    @Override
    public int insert(Timeline timeline) {
        int id = timelineMapper.insert(timeline);
        timeline.setId(id);
        return id;
    }

    @Autowired
    public void setTimelineMapper(TimelineMapper timelineMapper) {
        this.timelineMapper = timelineMapper;
    }
}
