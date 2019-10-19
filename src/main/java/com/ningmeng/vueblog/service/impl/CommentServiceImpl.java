package com.ningmeng.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.mapper.CommentMapper;
import com.ningmeng.vueblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

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
    @CacheEvict(cacheNames = "commentListByArticleId", key = "#comment.article.id")
    public Comment insert(Comment comment) {
        comment.setCreateTime(Instant.now().toEpochMilli());
        commentMapper.insertComment(comment);
        Integer id = commentMapper.selectOne(new QueryWrapper<Comment>()
                .eq("create_time", comment.getCreateTime())
                .eq("username", comment.getUsername())
        ).getId();
        return commentMapper.selectByCommentId(id);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "commentListByArticleId", key = "#comment.article.id"),
                    @CacheEvict(cacheNames = "comment", key = "#comment.id")
            })
    public int delete(Comment comment) {
        return commentMapper.deleteById(comment.getId());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "commentListByArticleId", key = "#article.id"),
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
                    @CacheEvict(cacheNames = "commentListByArticleId", key = "#article.id"),
                    @CacheEvict(cacheNames = "comment", allEntries = true)
            })
    public int delete(Integer id) {
        return commentMapper.deleteById(id);
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }
}
