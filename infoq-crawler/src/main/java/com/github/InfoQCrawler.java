package com.github;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2024/3/4 下午10:55
 * @Version 1.0
 */
@Slf4j
public class InfoQCrawler {

    public static void crawler() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        String url = "https://www.infoq.cn/public/v1/my/recommond";
        Request request = new Request.Builder()
                .url(url)
                .headers(getHeaders())
                .post(getRequestBody())
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            assert response.body() != null;
            String html = response.body().string();
            Document document = Jsoup.parse(html);
            log.info(html);
            log.info(document.title());

        } catch (IOException e) {
            log.error("error", e);
        }

    }

    private static RequestBody getRequestBody() {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("size", 30);
        final String jsonString = JSON.toJSONString(map);
        return RequestBody.create(mediaType, jsonString);
    }

    private static Headers getHeaders() {

        Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json, text/plain, */*");
        // map.put("Accept-Encoding", "gzip, deflate, br, zstd"); 需要注释掉，否则会引起乱码
        map.put("Accept-Language", "zh-CN,zh;q=0.9");
        map.put("Content-Type", "application/json; charset=utf-8");
        map.put("Host", "www.infoq.cn");
        map.put("Origin", "https://www.infoq.cn");
        map.put("Referer", "https://www.infoq.cn/");
        map.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");

        return Headers.of(map);
    }

    public static void main(String[] args) {
        crawler();
    }
}
