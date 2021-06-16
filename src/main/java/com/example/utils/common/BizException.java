package com.example.utils.common;

/**
 * @author: shihai.xie
 * @create: 2021-06-15 17:21
 * @description: 通用异常
 **/

public class BizException extends RuntimeException {
    /**
     * 错误码
     */
    protected String code = ResponseEnum.R_500.getCode();

    public BizException() {
        super();
    }


    public BizException(String message) {
        super(message);
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
