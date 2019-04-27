package com.ningmeng.vueblog.dao;

import com.ningmeng.vueblog.entity.Comment;

public interface CommentDAO {

    Comment selectById(int id);
}
