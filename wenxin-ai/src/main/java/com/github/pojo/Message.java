package com.github.pojo;

import lombok.*;

/**
 * @Author Dooby Kim
 * @Date 2024/3/6 下午11:11
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    private String role;
    private String content;
}
