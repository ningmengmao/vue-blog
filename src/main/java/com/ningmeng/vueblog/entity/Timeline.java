package com.ningmeng.vueblog.entity;

import lombok.Data;

/**
 * @Author: ningmengmao
 * @Date: 2019/11/5 下午4:55
 * @Version 1.0
 */
@Data
public class Timeline {
    private Integer id;
    private String title;
    private String content;
    private Long createTime;
}
