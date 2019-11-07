package com.ningmeng.vueblog.dto;

import lombok.Data;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/20 上午2:59
 * @Version 1.0
 */
@Data
public class GithubUserDTO {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
    private String html_url;
}
