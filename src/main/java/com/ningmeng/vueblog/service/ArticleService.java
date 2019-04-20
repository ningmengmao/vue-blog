package com.ningmeng.vueblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ningmeng.vueblog.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {


    IPage<Article> getArticlesByPageNumber(int pageNum);

    int update(Article article);

    void update(Iterable<Article> articles);

    Article findById(int id);

    IPage<Article> findByTagId(int id);

    IPage<Article> findByYearAndMonth(int year, int month);

    Article insert(Article article);

    List<Article> findById(Iterable<Integer> ids);
}
