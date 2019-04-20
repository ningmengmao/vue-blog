package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ningmeng.vueblog.entity.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Article> selectByTagId(int id);
}
