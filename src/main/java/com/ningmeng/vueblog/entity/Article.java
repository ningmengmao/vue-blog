package com.ningmeng.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "articleId", scope = Article.class)
public class Article implements Serializable {

    @TableId
    private Integer id;
    private String title;
    @TableField(value = "abstract")
    private String articleAbstract;
    private String content;
    private Integer views;
    private Long createTime;
    private Long updateTime;
    private Boolean isTop;

    @TableField(exist = false)
    private Set<Comment> commentSet = new HashSet<>();

    @TableField(exist = false)
    private Set<Tag> tagSet = new HashSet<>();
}
