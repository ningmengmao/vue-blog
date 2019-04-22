package com.ningmeng.vueblog.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ningmeng.vueblog.vo.LinkVO;

import java.io.Serializable;

@JsonIdentityInfo(property = "linkId",generator =  ObjectIdGenerators.IntSequenceGenerator.class )
public class Link implements Serializable {

    private Integer id;
    private String url;
    private String description;
    private String title;

    public Link() {
    }

    public Link(String url, String description, String title) {
        this.url = url;
        this.description = description;
        this.title = title;
    }

    public Link(LinkVO linkVO){
        this.id = linkVO.getId();
        this.description = linkVO.getDescription();
        this.url = linkVO.getUrl();
        this.title = linkVO.getTitle();
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
