package com.ningmeng.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="articleId", scope = Article.class)public class Article implements Serializable {

    private Integer id;
    private String title;
    @TableField(value = "abstract")
    private String articleAbstract;
    private String content;
    private Integer views;
    private Long createTime;
    private Long updateTime;
    private Boolean isTop;

    @TableField(exist = false)
    private Set<Comment> commentSet = new HashSet<>();

    @TableField(exist = false)
    private Set<Tag> tagSet = new HashSet<>();

    public Article() {
    }

    public Article(String title, String articleAbstract, String content, Integer views, Long createTime, Boolean isTop) {
        this.title = title;
        this.articleAbstract = articleAbstract;
        this.content = content;
        this.views = views;
        this.createTime = createTime;
        this.isTop = isTop;
    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", articleAbstract='" + articleAbstract + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isTop=" + isTop +
                '}';
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }

    public Set<Tag> getTagSet() {
        return tagSet;
    }

    public void setTagSet(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }
}
