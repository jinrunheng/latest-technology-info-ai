package com.github.pojo;

import lombok.*;

import java.util.List;

/**
 * @Author Dooby Kim
 * @Date 2024/3/6 下午11:09
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Messages {
    private List<Message> messages;
}
