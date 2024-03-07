package com.github.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author Dooby Kim
 * @Date 2024/3/6 下午11:34
 * @Version 1.0
 * @Desc Json 工具类
 */
public class JsonUtils {
    private JsonUtils() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 将Java对象转换为JSON字符串
    public static String toJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    // 将JSON字符串转换为Java对象
    public static <T> T fromJsonString(String jsonString, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, clazz);
    }

    // 如果你想避免在调用方法时处理异常，你可以将异常包装在RuntimeException中
    public static String toJsonStringSafe(Object object) {
        try {
            return toJsonString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting object to JSON string", e);
        }
    }

    public static <T> T fromJsonStringSafe(String jsonString, Class<T> clazz) {
        try {
            return fromJsonString(jsonString, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON string to object of type " + clazz.getName(), e);
        }
    }
}
