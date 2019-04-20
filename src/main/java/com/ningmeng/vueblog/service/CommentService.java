package com.ningmeng.vueblog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ningmeng.vueblog.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {

    IPage<Comment> getAllCommentsByArticleId(int id);


}
