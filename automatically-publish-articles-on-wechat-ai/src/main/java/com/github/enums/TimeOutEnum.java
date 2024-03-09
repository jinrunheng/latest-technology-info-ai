package com.github.enums;

/**
 * @Author Dooby Kim
 * @Date 2024/3/9 下午1:56
 * @Version 1.0
 */
public enum TimeOutEnum {
    TEN_DAYS(60 * 60 * 24 * 10),
    ONE_DAY(60 * 60 * 24);

    public final long timeout;

    TimeOutEnum(long timeout) {
        this.timeout = timeout;
    }
}
