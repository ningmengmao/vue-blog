package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;
import com.ningmeng.vueblog.entity.Tag;

import java.util.*;

public class ArticleVO {

    private Integer id;
    private String title;
    private String articleAbstract;
    private String content;
    private Integer views;
    private Long createTime;
    private Long updateTime;
    private Boolean isTop;
    private List<CommentVO> comments = new ArrayList<>();
    private Set<String> tags = new HashSet<>();
    private Boolean hasUpdate;


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
        this.hasUpdate = updateTime > createTime ;

        for (Tag tag : article.getTagSet())
            this.tags.add(tag.getTagName());
        ArrayList<Comment> commentss = new ArrayList<>(article.getCommentSet());
        commentss.sort((o1, o2) -> {
            if (o2.getFloorNumber() > o1.getFloorNumber())
                return 1;
            else if (o2.getFloorNumber().equals(o1.getFloorNumber()))
                return 0;
            else return -1;
        });
        for (Comment c : commentss)
            this.comments.add(new CommentVO(c));
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
                ", tags=" + tags +
                '}';
    }

    public Boolean getHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(Boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public List<CommentVO> getComments() {
        return comments;
    }

    public void setComments(List<CommentVO> comments) {
        this.comments = comments;
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

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
