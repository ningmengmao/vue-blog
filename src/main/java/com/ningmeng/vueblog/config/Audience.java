package com.ningmeng.vueblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt密钥配置
 * @Author: ningmengmao
 * @Date: 2019/10/21 下午6:00
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "audience")
@Component
public class Audience {
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;

}
