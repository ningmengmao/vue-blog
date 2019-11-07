package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.Timeline;

import java.util.List;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/5 下午5:02
 * @Version 1.0
 */
public interface TimelineService {

    List<Timeline> getAll();

    int insert(Timeline timeline);
}
