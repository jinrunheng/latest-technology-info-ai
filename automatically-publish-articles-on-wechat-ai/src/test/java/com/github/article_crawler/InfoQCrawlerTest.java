package com.github.article_crawler;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


/**
 * @Author Dooby Kim
 * @Date 2024/3/7 下午7:53
 * @Version 1.0
 */
class InfoQCrawlerTest {

    @Test
    void crawler() {
        assertDoesNotThrow(InfoQCrawler::crawler);
    }
}