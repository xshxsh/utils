package com.example.utils.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: shihai.xie
 * @create: 2021-06-15 17:20
 * @description: 全局异常拦截
 **/

@RestControllerAdvice
@Slf4j
public class BizExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Response<String> exceptionHandler(Exception e) {
        log.error("发生异常:", e);
        if (e instanceof BizException) {
            return Response.failResp(((BizException) e).getCode(), e.getMessage());
        }
        return Response.failResp(e.getMessage() == null ? ResponseEnum.R_500.getMsg() : e.getMessage());
    }
}
