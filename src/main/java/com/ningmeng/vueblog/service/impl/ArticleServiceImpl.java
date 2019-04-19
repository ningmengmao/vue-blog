package com.ningmeng.vueblog.service.impl;

import com.ningmeng.vueblog.annocation.ArticleAnnotation;
import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Tag;
import com.ningmeng.vueblog.repository.ArticleRepository;
import com.ningmeng.vueblog.service.ArticleService;
import com.ningmeng.vueblog.service.TagService;
import com.ningmeng.vueblog.vo.ArticleVO;
import com.ningmeng.vueblog.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
@CacheConfig(cacheNames = "articleVO")
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    private TagService tagService;

//    @Override
//    @Cacheable(cacheNames = "articlePage" ,key = "#pageNum")
//    @Transactional(readOnly = true)
//    public List<ArticleVO> getArticleByPageNum(int pageNum) {
//        if(pageNum < 1)
//            throw new RuntimeException("pageNum 必须大于0");
//        Page<Article> all = articleRepository.findAll(PageRequest.of(pageNum - 1, 8, Sort.by(Sort.Direction.DESC, "updateTime")));
//        Iterator<Article> iterator = all.iterator();
//        ArrayList<ArticleVO> articlesVO = new ArrayList<>();
//        while(iterator.hasNext()){
//            articlesVO.add(new ArticleVO(iterator.next()));
//        }
//        return articlesVO;
//    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "articlePage", key = "#methodName")
    public Page<ArticleVO> getArticles() {
        Page<ArticleVO> articleVOPage = new Page<>();
        List<Article> updateTime = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "updateTime"));
        //todo ARTICLE -> ARTICLEVO
        ArrayList<ArticleVO> articleVOS = new ArrayList<>();
        for (Article a : updateTime)
            articleVOS.add(new ArticleVO(a));
        articleVOPage.setData(articleVOS);
        return articleVOPage;
    }

    @Override
    @Cacheable(key = "#id")
    public ArticleVO findArticleVOById(int id) {
        return new ArticleVO(articleRepository.findById(id).get());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "articleVOByTagId", key = "#id")
    public Page<ArticleVO> findArticleVOByTagId(int id) {
        //todo
//        List<Integer> articleIdByTagId = articleRepository.findArticleIdByTagId(id);
//        Page<ArticleVO> articleVOPage = new Page<>();
//        for (int i : articleIdByTagId)
//            articleVOPage.getData().add(new ArticleVO(articleRepository.findById(i).get()));
//        return articleVOPage;
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "articleVOByYearAndMonth", key = "#args[0] + '-' + #args[1]")
    public Page<ArticleVO> findArticleVOByYearAndMonth(int year, int month) {

        int nextYear = year;
        int nextMonth = month + 1;
        if ( month == 12 ){
            nextYear = year + 1;
            nextMonth = 1;
        }

        LocalDateTime dateTime = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
        Instant from = Instant.from(dateTime);
        long begin = from.getEpochSecond();

        LocalDateTime next = LocalDateTime.of(nextYear, nextMonth, 1, 0, 0, 0, 0);
        Instant next1 = Instant.from(next);
        long end = next1.getEpochSecond();

        List<Article> allByUpdateTimeBetween = articleRepository.findAllByUpdateTimeBetween(begin, end);
        Page<ArticleVO> articleVOPage = new Page<>();
        for (Article a : allByUpdateTimeBetween)
            articleVOPage.getData().add(new ArticleVO(a));

        return articleVOPage;
    }


    @Caching(
            put = @CachePut(key = "#articleVO.id"),
            evict = @CacheEvict(cacheNames = "articlePage", allEntries = true)
    )

    /**
     * 只能更改正文和摘要和标题,必须携带updateTime
     */
    @Transactional
    @ArticleAnnotation(isIterable = false)
    public void update(ArticleVO articleVO){
        Article article = articleRepository.findById(articleVO.getId()).get();
        article.setUpdateTime(articleVO.getUpdateTime());
        article.setArticleAbstract(articleVO.getArticleAbstract());
        article.setContent(articleVO.getContent());
        article.setTitle(articleVO.getTitle());
        articleRepository.save(article);
    }

    @Transactional
    @ArticleAnnotation(isIterable = true)
    public void update(Iterable<ArticleVO> articlesVO){
        for (ArticleVO a: articlesVO)
            update(a);
    }

    @Transactional
    @Override
    public ArticleVO insert(ArticleVO articleVO) {
        Article article = new Article(articleVO);
        for (int i : articleVO.getTags()){
           Tag tag = tagService.getTagById(i);
           article.getTags().add(tag);
        }
        return new ArticleVO(articleRepository.save(article));
    }


    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }



    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
