package com.github.article_crawler;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Dooby Kim
 * @Date 2024/3/10 下午1:53
 * @Version 1.0
 * @Desc 爬取阮一峰科技爱好者周刊文章（每周五发布）；link：https://www.ruanyifeng.com/blog/
 */
@Slf4j
public class RyfArticleCrawler2 {
    private static final String url = "https://www.ruanyifeng.com/blog/";

    public static void crawler() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .headers(getHeaders())
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            assert response.body() != null;
            String html = response.body().string();
            Document document = Jsoup.parse(html);
            // <div id="content-inner">
            // asset-header
            // asset-name
            log.info(html);
            final Elements elementsByClass = document.getElementsByClass("asset-name");
            final List<Node> nodes = elementsByClass.get(0).childNodes();
            final String href = nodes.get(0).attributes().get("href");
            // 爬取文章 uri
            log.info("url", url);
            log.info(document.title());

        } catch (IOException e) {
            log.error("error", e);
        }


    }

    private static Headers getHeaders() {

        Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json, text/plain, */*");
        // map.put("Accept-Encoding", "gzip, deflate, br, zstd"); 需要注释掉，否则会引起乱码
        map.put("Accept-Language", "zh-CN,zh;q=0.9");
        map.put("Content-Type", "application/json; charset=utf-8");
        map.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");

        return Headers.of(map);
    }

    public static void main(String[] args) {
        crawler();
    }
}
