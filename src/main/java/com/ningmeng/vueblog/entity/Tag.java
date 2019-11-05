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
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="tagId", scope = Tag.class)
public class Tag implements Serializable {

    @TableId
    private Integer id;
    private String tagName;

    @TableField(exist = false)
    private Set<Article> articleSet = new HashSet<>();
}
