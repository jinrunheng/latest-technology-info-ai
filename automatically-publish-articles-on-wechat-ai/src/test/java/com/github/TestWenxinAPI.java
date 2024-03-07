package com.github;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @Author Dooby Kim
 * @Date 2024/3/6 下午10:42
 * @Version 1.0
 */
public class TestWenxinAPI {
    private String apiKey;
    private String secretKey;
    static final OkHttpClient httpClient = new OkHttpClient().newBuilder().build();

    private void initKey() {
        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream("wenxin-ai/src/main/resources/key.properties")) {
            // 从输入流加载属性列表
            prop.load(input);
            // 获取属性
            apiKey = prop.getProperty("API_KEY");
            secretKey = prop.getProperty("SECRET_KEY");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getAnswer(String content) throws IOException {
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
        final Map<String, String> map = JSON.parseObject(response.body().string(), Map.class);
        String token = map.get("access_token");
        return token;
    }

    public static void main(String[] args) throws IOException {
        TestWenxinAPI api = new TestWenxinAPI();
        api.initKey();
        final String answer = api.getAnswer("{\"messages\":[{\"role\":\"user\",\"content\":\"你好\"}]}");
        System.out.println(answer);
    }
}
