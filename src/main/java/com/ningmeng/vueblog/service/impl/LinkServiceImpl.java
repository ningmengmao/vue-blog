package com.ningmeng.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ningmeng.vueblog.entity.Link;
import com.ningmeng.vueblog.mapper.LinkMapper;
import com.ningmeng.vueblog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    private LinkMapper linkMapper;

    @Override
    @Cacheable(cacheNames = "allLinks", key = "1")
    public List<Link> getAllLinks() {
        return linkMapper.selectList(new QueryWrapper<Link>().orderByDesc("id"));
    }

    @Override
    @CacheEvict(cacheNames = "allLinks", allEntries = true)
    public int insert(Link link) {
        return linkMapper.insert(link);
    }

    @Override
    @CacheEvict(cacheNames = "allLinks", allEntries = true)
    public int delete(int id) {
        return linkMapper.deleteById(id);
    }

    @Autowired
    public void setLinkMapper(LinkMapper linkMapper) {
        this.linkMapper = linkMapper;
    }
}
