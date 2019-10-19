package com.ningmeng.vueblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;

import java.util.List;

public interface CommentService  {

    List<Comment> getCommentsByArticleId(int id);

    Comment getById(int id);

    Comment insert(Comment comment);

    int delete(Comment comment);

    int deleteByArticleId(int id);

    List<Comment> getNewComment();

    int getCommentTotal();

    IPage<Comment> getCommentsByPageNum(int pageNum);

    int delete(Integer id);
}
