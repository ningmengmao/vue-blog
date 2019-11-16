package com.ningmeng.vueblog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.entity.Tag;
import com.ningmeng.vueblog.mapper.ArticleMapper;
import com.ningmeng.vueblog.mapper.ArticleTagMapper;
import com.ningmeng.vueblog.service.ArticleService;
import com.ningmeng.vueblog.service.CommentService;
import com.ningmeng.vueblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl implements ArticleService {

    private final static int PAGE_SIZE = 8;

    private ArticleMapper articleMapper;
    private ArticleTagMapper articleTagMapper;

    private TagService tagService;
    private CommentService commentService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "articlePage", key = "#pageNum")
    public IPage<Article> getArticlesByPageNumber(int pageNum) {
        ArrayList<Article> articles = new ArrayList<>();
        if (pageNum < 1)
            throw new RuntimeException("页码必须大于0!!");

        return articleMapper.selectByPage(new Page<Article>(pageNum, PAGE_SIZE));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(
            put = @CachePut(key = "#article.id"),
            evict = {
                    @CacheEvict(cacheNames = "articlePage", allEntries = true),
                    @CacheEvict(cacheNames = "articleByTime", allEntries = true),
                    @CacheEvict(cacheNames = "article", key = "#article.id")
            }
    )
    public int update(Article article) {
        articleTagMapper.deleteByArticleId(article.getId());
        int i = articleMapper.updateById(article);
        // 维护中间表
        for (Tag tag : article.getTagSet())
            articleTagMapper.insert(article.getId(), tag.getId());
        return i ;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Iterable<Article> articles) {
        for (Article a : articles)
            update(a);
    }

    @Override
    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public Article findById(int id) {
        return articleMapper.selectByArticleId(id);
    }

    @Override
    @Cacheable(cacheNames = "articlePageByTagId", key = "#id + '-' + #pageNum")
    @Transactional(readOnly = true)
    public List<Article> findByTagId(int id, int pageNum) {
        if (pageNum < 1)
            throw new RuntimeException("页码必须大于0!!");
        return articleMapper.selectPageByTagId((pageNum - 1) * PAGE_SIZE, PAGE_SIZE, id);
    }

    @Override
    @Cacheable(cacheNames = "articleByTime", key = "#year + '-' + #month + '-' + #pageNum")
    @Transactional(readOnly = true)
    public IPage<Article> findByYearAndMonth(int year, int month, int pageNum) {
        int nextYear = year;
        int nextMonth = month + 1;
        if (month == 12){
            nextYear += 1;
            nextMonth = 1;
        }

        LocalDateTime begin = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(nextYear, nextMonth, 1, 0, 0, 0, 0);
        return articleMapper.findByUpdateTimeBetween(new Page(pageNum, 8), begin.toEpochSecond(ZoneOffset.ofHours(8)) * 1000, end.toEpochSecond(ZoneOffset.ofHours(8)) * 1000);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "articlePage", allEntries = true),
                    @CacheEvict(cacheNames = "articlePageByTagId", allEntries = true),
                    @CacheEvict(cacheNames = "articleByTime", allEntries = true)
            }
    )
    @Transactional
    public Article insert(Article article) {
        Instant now = Instant.now();
        article.setUpdateTime(now.toEpochMilli());
        article.setCreateTime(now.toEpochMilli());

        articleMapper.insert(article);

        int id = articleMapper.selectOne(new QueryWrapper<Article>()
                .eq("title", article.getTitle())
                .eq("create_time", article.getCreateTime())
                .eq("update_time", article.getUpdateTime())
                .eq("content", article.getContent())
                .eq("abstract", article.getArticleAbstract())
        ).getId();

        // 维护中间表关系
        for (Tag tag : article.getTagSet())
            articleTagMapper.insert(id, tag.getId());
        return findById(id);
    }

    @Override
    public List<Article> findById(Iterable<Integer> ids) {
        ArrayList<Article> articles = new ArrayList<>();
        for (int i : ids)
            articles.add(findById(i));
        return articles;
    }

    @Override
    public List<Article> getByMostComment() {
        return articleMapper.getByMostComment();
    }

    @Override
    public List<Article> getByMostView(){
        return articleMapper.getByMostView();
    }

    @Override
    public int getArticleTotal() {
        return articleMapper.selectCount(new QueryWrapper<Article>());
    }

    @Override
    public int getArticleTotalByYearAndMonth(Integer year, Integer month) {
        int nextYear = year;
        int nextMonth = month + 1;
        if (month == 12){
            nextYear += 1;
            nextMonth = 1;
        }

        long begin = LocalDateTime.of(year, month, 1, 0, 0, 0, 0).toEpochSecond(ZoneOffset.ofHours(8)) * 1000L;
        long end = LocalDateTime.of(nextYear, nextMonth, 1, 0, 0, 0, 0).toEpochSecond(ZoneOffset.ofHours(8)) * 1000L;

        return articleMapper.selectCount( new QueryWrapper<Article>()
            .between("update_time", begin, end)
        );
    }

    @Override
    public int lastArticleId() {
        return articleMapper.lastArticleId();
    }

    @Override
    public int getViewsTotal() {
        return articleMapper.getViewsTotal();
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "articlePage", allEntries = true),
                    @CacheEvict(cacheNames = "articlePageByTagId", allEntries = true),
                    @CacheEvict(cacheNames = "articleByTime", allEntries = true),
                    @CacheEvict(cacheNames = "article", key = "#id")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public int delete(int id) {
        articleTagMapper.deleteByArticleId(id);
        commentService.deleteByArticleId(id);
        int i = articleMapper.deleteById(id);
        return i;
    }

    @Override
    public Set<Comment> getParentCommentById(Integer articleId) {
        return commentService.getParentCommentByArticleId(articleId);
    }

    @Override
    public int findByTagIdTotal(Integer id) {
        return articleMapper.findByTagIdTotal(id);
    }

    @Override
    public List<Integer> articleIds() {
        return articleMapper.selectIds();
    }

    @Autowired
    public void setArticleTagMapper(ArticleTagMapper articleTagMapper) {
        this.articleTagMapper = articleTagMapper;
    }

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
}
