package com.ningmeng.vueblog.service;

import com.ningmeng.vueblog.entity.Link;

import java.util.List;

public interface LinkService {

    List<Link> getAllLinks();

    int insert(Link link);

    int delete(int id);

}
