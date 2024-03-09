package com.github.service;

import com.github.component.RedisOperator;
import com.github.enums.RedisKeyEnum;
import com.github.enums.TimeOutEnum;
import com.github.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;


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

    @Resource
    private RedisOperator redisOperator;

    private static final String wenxinToken = RedisKeyEnum.wenxin_token.key;

    public String getAnswer(String content) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        String token;
        if (Objects.isNull(redisOperator.get(wenxinToken))) {
            token = getAccessToken();
            // 存储到 redis 中
            redisOperator.set(wenxinToken, token);
            redisOperator.setExpire(wenxinToken, TimeOutEnum.TEN_DAYS.timeout); // 10 天
        } else {
            token = redisOperator.get(RedisKeyEnum.wenxin_token.key);
        }

        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token=" + token)
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
