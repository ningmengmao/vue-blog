package com.ningmeng.vueblog.dto;

import lombok.Data;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/20 上午2:59
 * @Version 1.0
 */
@Data
public class GithubTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;
}
