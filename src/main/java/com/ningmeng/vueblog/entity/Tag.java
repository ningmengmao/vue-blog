package com.ningmeng.vueblog.entity;

import com.fasterxml.jackson.annotation.*;
import com.ningmeng.vueblog.vo.TagVO;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="tagId", scope = Tag.class)
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Table(name = "b_tag")
@Entity
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler", "fieldHandler", "PersistentSet"})
public class Tag implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(targetEntity = Article.class, fetch = FetchType.EAGER ,mappedBy = "tags")
//    @JoinTable(name = "b_article_tag",
//        joinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id") },
//        inverseJoinColumns = { @JoinColumn(name = "article_id", referencedColumnName = "id") }
//    )
    private Set<Article> articles = new HashSet<>();


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

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
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
