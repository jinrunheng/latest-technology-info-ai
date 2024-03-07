package com.github.service;

import com.github.pojo.Message;
import com.github.pojo.Messages;
import com.github.utils.JsonUtils;
import lombok.SneakyThrows;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @Author Dooby Kim
 * @Date 2024/3/7 下午8:15
 * @Version 1.0
 */
class WenxinAIServiceTest {
    private static String apiKey;
    private static String secretKey;
    private static String keyPath = "src/test/resources/key.properties";
    private static final OkHttpClient httpClient = new OkHttpClient().newBuilder().build();

    private static void initKey() {
        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream(keyPath)) {
            // 从输入流加载属性列表
            prop.load(input);
            // 获取属性
            apiKey = prop.getProperty("API_KEY");
            secretKey = prop.getProperty("SECRET_KEY");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getAnswer(String content) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        String token = getAccessToken();
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions?access_token=" + token)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = httpClient.newCall(request).execute();
        final String string = response.body().string();
        return string;
    }

    public static String getAccessToken() throws IOException {
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

    @SneakyThrows
    @Test
    public void testApi() {
        initKey();
        Message message = new Message();
        message.setRole("user");
        message.setContent("你好");
        Messages messages = new Messages();
        messages.setMessages(Collections.singletonList(message));
        final String jsonString = JsonUtils.toJsonStringSafe(messages);
        assertDoesNotThrow(() -> getAnswer(jsonString));
    }
}