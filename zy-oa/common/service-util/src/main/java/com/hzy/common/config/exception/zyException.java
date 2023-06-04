package com.hzy.common.config.exception;

import com.hzy.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @title: zyException 自定义全局异常类
 * @Author zxwyhzy
 * @Date: 2023/6/4 13:51
 * @Version 1.0
 */
@Data
public class zyException extends RuntimeException{
    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param code
     * @param message
     */
    public zyException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public zyException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "zyException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
