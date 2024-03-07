package com.github.service;

import com.github.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;


/**
 * @Author Dooby Kim
 * @Date 2024/3/6 下午10:04
 * @Version 1.0
 */
@Service
@Slf4j
public class WenxinAIService {

    @Value("${API_KEY}")
    private String apiKey;

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Resource
    private OkHttpClient httpClient;

    public String getAnswer(String content) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = httpClient.newCall(request).execute();
        final Map<String, Object> map = JsonUtils.fromJsonString(response.body().string(), Map.class);
        return (String) map.get("result");
    }

    public String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + apiKey
                + "&client_secret=" + secretKey);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = httpClient.newCall(request).execute();
        assert response.body() != null;
        final Map<String, String> map = JsonUtils.fromJsonString(response.body().string(), Map.class);
        return map.get("access_token");
    }

}
