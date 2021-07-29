package com.example.utils.common;

/**
 * @author: shihai.xie
 * @create: 2021-06-15 11:40
 * @description: 响应码
 **/

public enum ResponseEnum {
    //http错误码
    R_200("200", "OK,成功"),
    R_400("400", "Bad Request,因发送的请求语法错误,服务器无法正常读取"),
    R_401("401", "Unauthorized,需要身份验证后才能获取所请求的内容"),
    R_403("403", "Forbidden,客户端没有权利访问所请求内容,服务器拒绝本次请求"),
    R_404("404", "Not Found,服务器找不到所请求的资源"),
    R_500("500", "Internal Server Error,服务端未知异常"),

    //业务错误码
    R_1000("1000", "参数校验异常");

    private String code;
    private String msg;

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 判断传入的响应码是否为200
     */
    public static boolean isSuccessful(int code) {
        return R_200.getCode().equals(String.valueOf(code));
    }

    /**
     * 判断传入的响应码是否为200
     */
    public static boolean isForbidden(int code) {
        return R_403.getCode().equals(String.valueOf(code));
    }


}
