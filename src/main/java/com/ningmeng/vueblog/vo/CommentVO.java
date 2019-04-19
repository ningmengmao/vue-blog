package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.Comment;

public class CommentVO {
    private Integer id;
    private String content;
    private Long createTime;
    private String username;
    private String userId;
    private String userThumbnailUrl; //头像url
    private String userUrl; //github url
    private Integer originalCommentId; //父评论id
    private Integer floorNumber;  //评论楼层数
    private Integer articleId;

    public CommentVO(){}

    public CommentVO(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createTime = comment.getCreateTime();
        this.username = comment.getUsername();
        this.userId = comment.getUserId();
        this.userThumbnailUrl = comment.getUserThumbnailUrl();
        this.userUrl = comment.getUserUrl();
        this.originalCommentId = comment.getOriginalCommentId();
        this.floorNumber = comment.getFloorNumber();
//        this.articleId = comment.getArticle().getId();
        //todo 关联
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", userThumbnailUrl='" + userThumbnailUrl + '\'' +
                ", userUrl='" + userUrl + '\'' +
                ", originalCommentId=" + originalCommentId +
                ", floorNumber=" + floorNumber +
                ", articleId=" + articleId +
                '}';
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

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
}
