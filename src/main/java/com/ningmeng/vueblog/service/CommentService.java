package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;

import java.util.List;

public interface CommentService  {

    List<Comment> getCommentsByArticleId(int id);

    Comment getById(int id);

    Comment insert(Comment comment);

    int delete(Comment comment);

    int delete(Article article);

}
