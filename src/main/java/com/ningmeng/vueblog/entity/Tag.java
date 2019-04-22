package com.ningmeng.vueblog.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ningmeng.vueblog.vo.TagVO;

import java.io.Serializable;
import java.util.Set;

@JsonIdentityInfo(property = "tagId",generator =  ObjectIdGenerators.IntSequenceGenerator.class )
public class Tag implements Serializable {

    private Integer id;
    private String tagName;

    @TableField(exist = false)
    private Set<Article> articleSet;

    public Tag(){}

    public Tag(TagVO tagVO){
        this.id = tagVO.getId();
        this.tagName = tagVO.getTagName();
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                '}';
    }

    public Set<Article> getArticleSet() {
        return articleSet;
    }

    public void setArticleSet(Set<Article> articleSet) {
        this.articleSet = articleSet;
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
