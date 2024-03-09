package com.github.enums;

/**
 * @Author Dooby Kim
 * @Date 2024/3/9 下午1:28
 * @Version 1.0
 * @Desc 文心一言 Token key
 */
public enum RedisKeyEnum {

    wenxin_token("wenxin_token");

    public final String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }
}
