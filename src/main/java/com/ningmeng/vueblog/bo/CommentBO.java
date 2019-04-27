package com.ningmeng.vueblog.bo;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Comment;

import java.io.Serializable;

public class CommentBO implements Serializable {

    private Integer id;
    private String content;
    private Long createTime;
    private String username;
    private String userId;
    private String userThumbnailUrl; //头像url
    private String userUrl; //github url
    private Integer originalCommentId; //父评论id
    private Integer floorNumber;  //评论楼层数
    private Article article;

    public CommentBO(){}

    public CommentBO(Comment comment, Article article){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createTime = comment.getCreateTime();
        this.username = comment.getUsername();
        this.userId = comment.getUserId();
        this.userThumbnailUrl = comment.getUserThumbnailUrl();
        this.userUrl = comment.getUserUrl();
        this.originalCommentId = comment.getOriginalCommentId();
        this.floorNumber = comment.getFloorNumber();
        this.article = article;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserThumbnailUrl() {
        return userThumbnailUrl;
    }

    public void setUserThumbnailUrl(String userThumbnailUrl) {
        this.userThumbnailUrl = userThumbnailUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public Integer getOriginalCommentId() {
        return originalCommentId;
    }

    public void setOriginalCommentId(Integer originalCommentId) {
        this.originalCommentId = originalCommentId;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
