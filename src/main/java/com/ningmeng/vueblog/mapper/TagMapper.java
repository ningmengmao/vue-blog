package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ningmeng.vueblog.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectByArticleId(int id);

    List<Tag> getAll();

    Tag selectByTagId(Integer id);

    Tag getByName(@Param("name") String name);

    Integer getArticleTotalById(int id);

}
