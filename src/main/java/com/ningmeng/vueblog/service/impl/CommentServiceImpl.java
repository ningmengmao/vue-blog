package com.ningmeng.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.mapper.CommentMapper;
import com.ningmeng.vueblog.service.CommentService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "commentListByArticleId", key = "#id")
    public List<Comment> getCommentsByArticleId(int id) {
        return commentMapper.selectByArticleId(id);
    }

    @Override
    @Cacheable(cacheNames = "comment", key = "#id")
    public Comment getById(int id) {
        return commentMapper.selectByCommentId(id);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "commentListByArticleId", key = "#comment.articleId")
    public Comment insert(Comment comment) {
        comment.setCreateTime(Instant.now().toEpochMilli());
        commentMapper.insertComment(comment);
        return comment;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "commentListByArticleId", key = "#comment.id"),
                    @CacheEvict(cacheNames = "comment", key = "#comment.id")
            })
    public int delete(Comment comment) {
        return commentMapper.deleteById(comment.getId());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "commentListByArticleId", key = "#id"),
                    @CacheEvict(cacheNames = "comment", allEntries = true)
            })
    public int deleteByArticleId(int id) {
        return commentMapper.delete(new QueryWrapper<Comment>()
                    .eq("article_id", id)
        );
    }

    @Override
    public List<Comment> getNewComment() {
        return commentMapper.getNewComment();
    }

    @Override
    public int getCommentTotal() {
        return commentMapper.selectCount(new QueryWrapper<Comment>());
    }

    @Override
    public IPage<Comment> getCommentsByPageNum(int pageNum) {
        if (pageNum < 1){
            throw new RuntimeException("pageNum 必须大于0");
        }
        return commentMapper.getComments(new Page<Comment>(pageNum, 15));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "commentListByArticleId", key = "#id"),
                    @CacheEvict(cacheNames = "comment", allEntries = true)
            })
    public int delete(Integer id) {
        return commentMapper.deleteById(id);
    }

    @Override
    public List<Comment> getCommentsByOriginalCommentId(Integer id) {
        return commentMapper.selectByOriginalCommentId(id);
    }

    @Override
    public Set<Comment> getParentCommentByArticleId(Integer articleId) {
        return commentMapper.getParentCommentByArticleId(articleId);
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
}
