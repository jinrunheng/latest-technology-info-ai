package com.github.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author Dooby Kim
 * @Date 2024/3/6 下午10:01
 * @Version 1.0
 */
@Configuration
@PropertySource("classpath:key.properties")
public class WenxinConfig {
    @Bean
    public OkHttpClient httpClient() {
        return new OkHttpClient().newBuilder().build();
    }
}
