package com.github.controller;

import com.github.pojo.Messages;
import com.github.service.WenxinAIService;
import com.github.utils.CustomJsonResult;
import com.github.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author Dooby Kim
 * @Date 2024/3/6 下午10:21
 * @Version 1.0
 */
@RestController
@RequestMapping("wenxin")
@Slf4j
@Api(tags = {"文心一言调用相关接口"})
public class WenxinAIController {

    @Resource
    private WenxinAIService wenxinAIService;

    @PostMapping("/chat")
    @ApiOperation(value = "文心一言对话接口")
    public CustomJsonResult chat(@RequestBody Messages messages) {
        final String jsonString = JsonUtils.toJsonStringSafe(messages);
        String answer = null;
        try {
            answer = wenxinAIService.getAnswer(jsonString);
        } catch (IOException exception) {
            log.error("error", exception);
        }
        return CustomJsonResult.ok(answer);
    }
}
