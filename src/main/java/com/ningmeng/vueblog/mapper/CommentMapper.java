package com.ningmeng.vueblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ningmeng.vueblog.entity.Comment;

import java.util.List;
import java.util.Set;


public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> selectByArticleId(int id);

    Comment selectByCommentId(int id);

    Integer insertComment(Comment comment);

    List<Comment> getNewComment();

    IPage<Comment> getComments(Page page);

    List<Comment> selectByOriginalCommentId(int originalCommentId);

    Set<Comment> getParentCommentByArticleId(Integer articleId);
}
