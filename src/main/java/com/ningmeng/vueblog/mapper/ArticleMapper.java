package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ningmeng.vueblog.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ArticleMapper extends BaseMapper<Article> {


    List<Article> selectPageByTagId(@Param("start") int start, @Param("pageSize") int pageSize, @Param("tagId") int id);

    Article selectByArticleId(int id);

    IPage<Article> findByUpdateTimeBetween(Page page, @Param("begin") long begin, @Param("end") long end);

    IPage<Article> findByCreateTimeBetween(Page page, @Param("begin") long begin, @Param("end") long end);

    IPage<Article> selectByPage(Page page);

    List<Article> getByMostComment();

    List<Article> getByMostView();

    Integer lastArticleId();

    Integer getViewsTotal();

    List<Integer> selectIds();

    int findByTagIdTotal(@Param("tag_id") Integer id);
}
