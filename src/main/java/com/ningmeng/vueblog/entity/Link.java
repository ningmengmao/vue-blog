package com.ningmeng.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import java.io.Serializable;

@Data
public class Link implements Serializable {
    @TableId
    private Integer id;
    private String url;
    private String description;
    private String title;
}
