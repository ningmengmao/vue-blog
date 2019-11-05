package com.ningmeng.vueblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/24 上午10:34
 * @Version 1.0
 */

@Component
public class BaiduAccessProvider {

    private static final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    @Value("${baidu.api-key}")
    private String apiKey;

    @Value("${baidu.secret-key}")
    private String secretKey;


    /**
     * post请求的响应
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     * Cache-Control: no-store
     * <p>
     * {
     * "access_token": "1.a6b7dbd428f731035f771b8d15063f61.86400.1292922000-2346678-124328",
     * "expires_in": 86400,
     * "refresh_token": "2.385d55f8615fdfd9edb7c4b5ebdc3e39.604800.1293440400-2346678-124328",
     * "scope": "basic email",
     * "session_key": "ANXxSNjwQDugf8615OnqeikRMu2bKaXCdlLxn",
     * "session_secret": "248APxvxjCZ0VEC43EYrvxqaK4oZExMB",
     * }
     *
     * @param code 百度传来的code
     * @return token
     */
    public String getAccessToken(String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("grant_type", "authorization_code");
        map.put("code", code);
        map.put("client_id", apiKey);
        map.put("client_secret", secretKey);
        map.put("redirect_url", "http://localhost:23333/baiduCallback");

        RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(map));
        Request request = new Request.Builder()
                .url("https://openapi.baidu.com/oauth/2.0/token")
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            JSONObject jsonResult = (JSONObject) JSON.parse(response.body().string());
            String accessToken = jsonResult.containsKey("access_token") ? (String) jsonResult.get("access_token") : null;
            return accessToken;
        } catch (IOException e) {
            throw new RuntimeException("获取信息失败");
        }
    }
}
