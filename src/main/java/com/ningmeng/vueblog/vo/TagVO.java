package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.Article;
import com.ningmeng.vueblog.entity.Tag;

import java.util.HashSet;
import java.util.Set;

public class TagVO {

    private Integer id;
    private String tagName;
    private Set<Integer> articles = new HashSet<>();
    public TagVO(){}

    public TagVO(Tag tag){
        this.id = tag.getId();
        this.tagName = tag.getTagName();
        for (Article article : tag.getArticles())
            this.articles.add(article.getId());
    }

    @Override
    public String toString() {
        return "TagVO{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                ", articles=" + articles +
                '}';
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

    public Set getArticles() {
        return articles;
    }

    public void setArticles(Set articles) {
        this.articles = articles;
    }
}
