package com.ningmeng.vueblog.dao;

import com.ningmeng.vueblog.entity.Article;

import java.util.List;

public interface ArticleDAO {

    List<Article> selectByTagId(int id, int pageNum, int pageSize);

    Article selectByArticleId(int id);

}
