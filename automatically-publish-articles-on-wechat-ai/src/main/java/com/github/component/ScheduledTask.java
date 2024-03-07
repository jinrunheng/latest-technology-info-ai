package com.github.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author Dooby Kim
 * @Date 2024/3/7 下午10:26
 * @Version 1.0
 * @Desc 定时任务
 */
@Component
@Slf4j
public class ScheduledTask {

    /**
     * 每分钟执行一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void doTask() {
        log.info("-------- exec scheduled task --------");
    }
}
