package com.ningmeng.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.mapper.ArticleMapper;
import com.ningmeng.vueblog.service.ArticleService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    @Cacheable(cacheNames = "newsArticlePage" ,key = "#pageNum")
    public IPage<Article> getArticlesByPageNumber(int pageNum) {
        if (pageNum < 1)
            throw new RuntimeException("pageNumber 必须大于 0");
        return baseMapper.selectPage(new Page<Article>(pageNum, 8),
                new QueryWrapper<Article>()
                        .orderByDesc("update_time")
        );
    }

    @Override
    @Caching(
            put = @CachePut(key = "#article.id"),
            evict = @CacheEvict(cacheNames = "newsArticlePage", allEntries = true)
    )
    public int update(Article article) {
        return baseMapper.updateById(article);
    }

    @Override
    public void update(Iterable<Article> articles) {
        for (Article a : articles)
            update(a);
    }

    @Override
    @Cacheable(key = "#id")
    public Article findById(int id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Cacheable(cacheNames = "findByTagId", key = "#id")
    public IPage<Article> findByTagId(int id) {
        return null;
    }

    @Override
    public IPage<Article> findByYearAndMonth(int year, int month) {
        return null;
    }

    @Override
    public Article insert(Article article) {
        return null;
    }

    @Override
    public List<Article> findById(Iterable<Integer> ids) {
        return null;
    }
}
