package com.yzb.exception;

/**
 * @description: 解析HTTP请求异常类
 * @author: BeckoninGshy
 * @create: 2021/1/24 16:33
 */
public class ParseHttpRequestException extends Exception {
    private static final long serialVersionUID = 54839657436794L;

    public ParseHttpRequestException() {
    }

    public ParseHttpRequestException(String message) {
        super(message);
    }
}
