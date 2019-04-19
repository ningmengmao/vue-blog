package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.vo.ArticleVO;
import com.ningmeng.vueblog.vo.Page;

public interface ArticleService {


    Page<ArticleVO> getArticles();

    void update(ArticleVO articleVO);

    void update(Iterable<ArticleVO> articlesVO);

    ArticleVO findArticleVOById(int id);

    Page<ArticleVO> findArticleVOByTagId(int id);

    Page<ArticleVO> findArticleVOByYearAndMonth(int year, int month);

    ArticleVO insert(ArticleVO articleVO);


}
