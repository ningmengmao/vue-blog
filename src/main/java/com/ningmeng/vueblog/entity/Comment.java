package com.ningmeng.vueblog.entity;

import com.fasterxml.jackson.annotation.*;
import com.ningmeng.vueblog.vo.CommentVO;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.Proxy;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="commentId", scope = Comment.class)
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table(name = "b_comment")
@Entity
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler", "fieldHandler", "PersistentSet"})
public class Comment implements Serializable {

    @GeneratedValue
    @Id
    private Integer id;
    private String content;

    private Long createTime;
    private String username;
    private String userId;

    private String userThumbnailUrl; //头像url
    private String userUrl; //github url
    private Integer originalCommentId; //父评论id

    private Integer floorNumber;  //评论楼层数


    @ManyToOne( fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "article_id")
    private Article article;

    public Comment() {
    }

    public Comment(CommentVO commentVO){
        this.id = commentVO.getId();
        this.content = commentVO.getContent();
        this.createTime = commentVO.getCreateTime();
        this.username = commentVO.getUsername();
        this.userId = commentVO.getUserId();
        this.userThumbnailUrl = commentVO.getUserThumbnailUrl();
        this.userUrl = commentVO.getUserUrl();
        this.originalCommentId = commentVO.getOriginalCommentId();
        this.floorNumber = commentVO.getFloorNumber();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", userThumbnailUrl='" + userThumbnailUrl + '\'' +
                ", userUrl='" + userUrl + '\'' +
                ", originalCommentId=" + originalCommentId +
                ", floorNumber=" + floorNumber +
                '}';
    }

    public Comment(String content, Long createTime, String username, String userId, String userThumbnailUrl, String userUrl, Integer originalCommentId, Integer floorNumber) {
        this.content = content;
        this.createTime = createTime;
        this.username = username;
        this.userId = userId;
        this.userThumbnailUrl = userThumbnailUrl;
        this.userUrl = userUrl;
        this.originalCommentId = originalCommentId;
        this.floorNumber = floorNumber;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
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
}