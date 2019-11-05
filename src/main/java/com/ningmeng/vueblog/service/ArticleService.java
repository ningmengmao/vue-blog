package com.ningmeng.vueblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;

import java.util.List;
import java.util.Set;


public interface ArticleService  {


    IPage<Article> getArticlesByPageNumber(int pageNum);

    int update(Article article);

    void update(Iterable<Article> articles);

    Article findById(int id);

    IPage<Article> findByTagId(int id, int pageNum);

    IPage<Article> findByYearAndMonth(int year, int month, int pageNum);

    Article insert(Article article);

    List<Article> findById(Iterable<Integer> ids);

    List<Article> getByMostView();

    int getArticleTotal();

    int getArticleTotalByYearAndMonth(Integer year, Integer month);

    List<Article> getByMostComment();

    int lastArticleId();

    List<Integer> articleIds();

    int getViewsTotal();

    int delete(int id);

    Set<Comment> getParentCommentById(Integer articleId);
}
