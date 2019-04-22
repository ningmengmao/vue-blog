package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ningmeng.vueblog.entity.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> selectByArticleId(int id);
}
