package com.ningmeng.vueblog.provider;

import com.alibaba.fastjson.JSON;
import com.ningmeng.vueblog.dto.GithubTokenDTO;
import com.ningmeng.vueblog.dto.GithubUserDTO;
import com.ningmeng.vueblog.exception.GithubTimeOutException;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/20 上午2:55
 * @Version 1.0
 */
@Component
public class GithubAccessProvider {

    private static final MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client = new OkHttpClient();

    public String getAccessToken(GithubTokenDTO githubTokenDTO) throws GithubTimeOutException {

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(githubTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new GithubTimeOutException("获取信息失败");
        }
    }

    public GithubUserDTO getGithubUserDTO(String token) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?" + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            return JSON.parseObject(string, GithubUserDTO.class);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
