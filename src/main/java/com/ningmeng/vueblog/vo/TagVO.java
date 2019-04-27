package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Tag;

import java.util.HashSet;
import java.util.Set;

public class TagVO {

    private Integer id;
    private String tagName;
    public TagVO(){}

    public TagVO(Tag tag){
        this.id = tag.getId();
        this.tagName = tag.getTagName();
    }

    @Override
    public String toString() {
        return "TagVO{" +
                "id=" + id +
                ", tagName='" + tagName +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
