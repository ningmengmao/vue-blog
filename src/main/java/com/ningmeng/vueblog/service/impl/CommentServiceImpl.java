package com.ningmeng.vueblog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public IPage<Comment> getAllCommentsByArticleId(int id) {
        return null;
    }
}
