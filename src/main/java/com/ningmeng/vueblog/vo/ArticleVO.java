package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.entity.Tag;

import java.util.HashSet;
import java.util.Set;

public class ArticleVO {

    private Integer id;
    private String title;
    private String articleAbstract;
    private String content;
    private Integer views;
    private Long createTime;
    private Long updateTime;
    private Boolean isTop;
    private Set<Integer> comments = new HashSet<>();
    private Set<Integer> tags = new HashSet<>();


    public ArticleVO(){}

    public ArticleVO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.articleAbstract = article.getArticleAbstract();
        this.content = article.getContent();
        this.views = article.getViews();
        this.createTime = article.getCreateTime();
        this.updateTime = article.getUpdateTime();
        this.isTop = article.getTop();
        for (Tag tag : article.getTags())
            this.tags.add(tag.getId());
        for (Comment comment : article.getComments())
            this.comments.add(comment.getId());
    }

    @Override
    public String toString() {
        return "ArticleVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", articleAbstract='" + articleAbstract + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isTop=" + isTop +
                ", comments=" + comments +
                ", tags=" + tags +
                '}';
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

    public Set<Integer> getComments() {
        return comments;
    }

    public void setComments(Set<Integer> comments) {
        this.comments = comments;
    }

    public Set<Integer> getTags() {
        return tags;
    }

    public void setTags(Set<Integer> tags) {
        this.tags = tags;
    }
}
