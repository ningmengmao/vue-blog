package com.ningmeng.vueblog.bo;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.entity.Tag;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ArticleBO implements Serializable {

    private Integer id;
    private String title;
    private String articleAbstract;
    private String content;
    private Integer views;
    private Long createTime;
    private Long updateTime;
    private Boolean isTop;
    private Set<Comment> commentSet;
    private Set<TagBO> tagSet;

    public ArticleBO(){}

    public ArticleBO(Article article, HashSet<Comment> comments, HashSet<TagBO> tags) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.articleAbstract = article.getArticleAbstract();
        this.content = article.getContent();
        this.views = article.getViews();
        this.createTime = article.getCreateTime();
        this.updateTime = article.getUpdateTime();
        this.isTop = article.getTop();
        this.commentSet = comments;
        this.tagSet = tags;
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

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }

    public Set<TagBO> getTagSet() {
        return tagSet;
    }

    public void setTagSet(Set<TagBO> tagSet) {
        this.tagSet = tagSet;
    }
}
