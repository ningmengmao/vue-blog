package com.ningmeng.vueblog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
public class User implements Serializable {
    @TableId
    private Integer id;
    private String username;
    private Long accountId;
    private String userUrl;
    private String avatarUrl;
    private Long createTime;
    private String bio;
}
