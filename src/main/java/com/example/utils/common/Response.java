package com.example.utils.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: shihai.xie
 * @create: 2021-06-15 16:44
 * @description: 统一返回类
 **/

@Data
public class Response<T> implements Serializable {
    //响应码(6位)
    private String code = ResponseEnum.R_200.getCode();
    //响应信息
    private String msg = ResponseEnum.R_200.getMsg();
    //响应内容
    private T data;

    public static Response<String> successResp() {
        return new Response<>();
    }

    public static <T> Response<T> successResp(T data) {
        final Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    public static Response<String> failResp(String msg) {
        final Response<String> response = new Response<>();
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> failResp(String code, String msg) {
        final Response<T> response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

}
