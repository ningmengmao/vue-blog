package com.ningmeng.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ningmeng.vueblog.bo.TagBO;
import com.ningmeng.vueblog.entity.Tag;
import com.ningmeng.vueblog.mapper.ArticleTagMapper;
import com.ningmeng.vueblog.mapper.TagMapper;
import com.ningmeng.vueblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * tag_article关系由article维护
 */
@Service
public class TagServiceImpl implements TagService {

    private TagMapper tagMapper;
    private ArticleTagMapper articleTagMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "allTag")
    public List<Tag> getAllTag() {
        return tagMapper.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "tagsByArticleId", key = "#id")
    public List<Tag> getByArticleId(int id) {
        return tagMapper.selectByArticleId(id);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "allTag", allEntries = true),
                    @CacheEvict(cacheNames = "tagsByArticleId", allEntries = true)
            }
    )
    public Tag insert(Tag tag) {
        tagMapper.insert(tag);
        Integer id = tagMapper.selectOne(new QueryWrapper<Tag>()
                .eq("tag_name", tag.getTagName())
        ).getId();
        return tagMapper.selectByTagId(id);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "allTag", allEntries = true),
                    @CacheEvict(cacheNames = "tagsByArticleId", allEntries = true),
                    @CacheEvict(cacheNames = "tag", key = "#id")
            }
    )
    public int delete(int id) {
        int i = tagMapper.deleteById(id);
        articleTagMapper.deleteByTagId(id);
        return i;
    }

    @Override
    @Cacheable(cacheNames = "tag", key = "#id")
    public Tag findById(int id) {
        return tagMapper.selectByTagId(id);
    }

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Autowired
    public void setArticleTagMapper(ArticleTagMapper articleTagMapper) {
        this.articleTagMapper = articleTagMapper;
    }
}
