package com.ningmeng.vueblog.vo;

import com.ningmeng.vueblog.entity.Link;

public class LinkVO {

    private Integer id;
    private String url;
    private String description;
    private String title;


    public LinkVO(){}

    public LinkVO(Link link){
        this.id = link.getId();
        this.description = link.getDescription();
        this.url = link.getUrl();
        this.title = link.getTitle();
    }

    @Override
    public String toString() {
        return "LinkVO{" +
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
