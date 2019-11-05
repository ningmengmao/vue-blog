package com.ningmeng.vueblog.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

@ToString
@Setter
@Getter
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="commentId", scope = Comment.class)
public class Comment implements Serializable {

    @TableId
    private Integer id;
    private String content;
    private Long createTime;

    @TableField(exist = false)
    private User user;
    private Integer originalCommentId;
    private Integer articleId;

}
