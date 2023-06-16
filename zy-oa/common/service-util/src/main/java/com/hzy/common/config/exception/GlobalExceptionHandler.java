package com.hzy.common.config.exception;

import com.hzy.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @title: GlobalExceptionHandler 全局异常处理类
 * @Author zxwyhzy
 * @Date: 2023/6/4 13:17
 * @Version 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        log.error("发生异常",e);
        return Result.fail().message(e.getMessage());
    }

    @ExceptionHandler(zyException.class)
    @ResponseBody
    public Result error(zyException e){
        log.error(e.getMessage(),e);
        return Result.fail()
                .code(e.getCode())
                .message(e.getMessage());
    }
}
