package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ningmeng.vueblog.entity.Article;
import org.apache.ibatis.annotations.Param;


public interface ArticleMapper extends BaseMapper<Article> {

    IPage<Article> selectByTagId(Page page, @Param("tagId") int id);

    Article selectByArticleId(int id);

    IPage<Article> findByUpdateTimeBetween(Page page, @Param("begin") long begin, @Param("end") long end);

    IPage<Article> findByCreateTimeBetween(Page page, @Param("begin") long begin, @Param("end") long end);

    IPage<Article> selectByPage(Page page);

}
