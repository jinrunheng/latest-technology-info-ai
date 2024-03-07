package com.github.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author Dooby Kim
 * @Date 2024/3/7 下午9:43
 * @Version 1.0
 * @Desc 定义返回 Result
 */
public class CustomJsonResult {
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    @JsonIgnore
    private String ok;

    public static CustomJsonResult build(Integer status, String msg, Object data) {
        return new CustomJsonResult(status, msg, data);
    }

    public static CustomJsonResult build(Integer status, String msg, Object data, String ok) {
        return new CustomJsonResult(status, msg, data, ok);
    }

    public static CustomJsonResult ok(Object data) {
        return new CustomJsonResult(data);
    }

    public static CustomJsonResult ok() {
        return new CustomJsonResult(null);
    }

    public static CustomJsonResult errorMsg(String msg) {
        return new CustomJsonResult(500, msg, null);
    }

    public static CustomJsonResult errorMap(Object data) {
        return new CustomJsonResult(501, "error", data);
    }

    public static CustomJsonResult errorTokenMsg(String msg) {
        return new CustomJsonResult(502, msg, null);
    }

    public static CustomJsonResult errorException(String msg) {
        return new CustomJsonResult(555, msg, null);
    }

    public static CustomJsonResult errorUserQQ(String msg) {
        return new CustomJsonResult(556, msg, null);
    }

    public CustomJsonResult() {
    }

    public CustomJsonResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public CustomJsonResult(Integer status, String msg, Object data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    public CustomJsonResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

}
