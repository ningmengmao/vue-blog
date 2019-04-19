package com.ningmeng.vueblog.entity;

import com.fasterxml.jackson.annotation.*;
import com.ningmeng.vueblog.vo.ArticleVO;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="articleId", scope = Article.class)
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table(name = "b_article")
@Entity
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler", "fieldHandler", "PersistentSet"})
public class Article implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String title;

    @Column(name = "abstract")
    private String articleAbstract;
    private String content;
    private Integer views;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

    @Column(name = "is_top")
    private Boolean isTop;

    @ManyToMany(targetEntity = Tag.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "b_article_tag",
            joinColumns = { @JoinColumn(name = "article_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id") }
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<>();

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

    public Article(ArticleVO articleVO){
        this.id = articleVO.getId();
        this.title = articleVO.getTitle();
        this.articleAbstract = articleVO.getArticleAbstract();
        this.content = articleVO.getContent();
        this.views = articleVO.getViews();
        this.createTime = articleVO.getCreateTime();
        this.updateTime = articleVO.getUpdateTime();
        this.isTop = articleVO.getTop();
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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
