package com.ningmeng.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.mapper.ArticleMapper;
import com.ningmeng.vueblog.mapper.TagMapper;
import com.ningmeng.vueblog.service.ArticleService;
import com.ningmeng.vueblog.service.CommentService;
import com.ningmeng.vueblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@CacheConfig(cacheNames = "article")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private ArticleMapper articleMapper;

    private TagService tagService;
    private CommentService commentService;

    @Override
    @Cacheable(cacheNames = "newsArticlePage" ,key = "#pageNum")
    public IPage<Article> getArticlesByPageNumber(int pageNum) {
        if (pageNum < 1)
            throw new RuntimeException("pageNumber 必须大于 0");
        return articleMapper.selectPage(new Page<Article>(pageNum, 8),
                new QueryWrapper<Article>()
                        .orderByDesc("update_time")
        );
    }

    /**
     * 只更新文章,不更新评论和tag
     * @param article
     * @return
     */
    @Override
    @Caching(
            put = @CachePut(key = "#article.id"),
            evict = @CacheEvict(cacheNames = "newsArticlePage", allEntries = true)
    )
    public int update(Article article) {
        return articleMapper.updateById(article);
    }

    @Override
    public void update(Iterable<Article> articles) {
        for (Article a : articles)
            update(a);
    }

    @Override
    @Cacheable(key = "#id")
    public Article findById(int id) {
        // todo 维护关系
        return articleMapper.selectById(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(cacheNames = "findByTagId", key = "#id")
    public IPage<Article> findByTagId(int id) {
        List<Article> articles = articleMapper.selectByTagId(id);
        Page<Article> articlePage = new Page<>(1, 8, articles.size());
        articlePage.setRecords(articles);
        return articlePage;
    }

    @Override
    public IPage<Article> findByYearAndMonth(int year, int month) {
        int nextYear = year;
        int nextMonth = month;
        if ( month == 12){
            nextMonth = 1;
            nextYear += 1;
        }
        LocalDateTime begin = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(nextYear, nextMonth, 1, 0, 0, 0);
        IPage<Article> articleIPage = articleMapper.selectPage(new Page<Article>(),
                new QueryWrapper<Article>()
                        .between("update_time", begin.toEpochSecond(ZoneOffset.ofHours(8)) * 1000, end.toEpochSecond(ZoneOffset.ofHours(8)) * 1000)
                        .orderByDesc("update_time")
        );
        for (Article a : articleIPage.getRecords()){

        }


        return null;
    }

    @Override
    public Article insert(Article article) {

        return null;
    }

    @Override
    public List<Article> findById(Iterable<Integer> ids) {
        ArrayList<Article> articles = new ArrayList<>();
        for (int i : ids)
            articles.add(findById(i));
        return articles;
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
