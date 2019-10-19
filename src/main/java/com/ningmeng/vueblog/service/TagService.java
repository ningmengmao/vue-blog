package com.ningmeng.vueblog.service;


import com.ningmeng.vueblog.entity.Tag;

import java.util.List;

public interface TagService {

    Tag findById(int id);

    List<Tag> getAllTag();

    List<Tag> getByArticleId(int id);

    Tag insert(Tag tag);

    int delete(int id);

    Tag getByName(String name);

    int getArticleTotalById(int id);

}
