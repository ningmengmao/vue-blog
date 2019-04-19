package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllCommentsByArticleId(int id);
    Comment getOneComment(int id);

}
