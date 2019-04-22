package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ningmeng.vueblog.entity.Comment;

import java.util.List;


public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> selectByArticleId(int id);
}
