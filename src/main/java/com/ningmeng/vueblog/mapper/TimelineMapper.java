package com.ningmeng.vueblog.mapper;

import com.ningmeng.vueblog.entity.Timeline;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/5 下午5:09
 * @Version 1.0
 */
public interface TimelineMapper {

    @Select("select * from b_timeline order by create_time desc")
    List<Timeline> getAllTimeline();

    @Insert("insert into b_timeline (title, content, create_time) " +
            "values(#{title}, #{content}, #{createTime})")
    int insert(Timeline timeline);
}
