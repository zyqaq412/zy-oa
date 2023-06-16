package com.hzy.common.result;

/**
 * @title: ResultCodeEnum 统一返回结果状态信息类
 * @Author zxwyhzy
 * @Date: 2023/6/3 23:04
 * @Version 1.0
 */

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    LOGIN_ERROR(204, "认证失败"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
