package com.huanghehua.www.framework.ioc.exception;

/**
 * 解析异常，一般在解析配置文件时抛出异常。
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/31
 */
public class ParseException extends RuntimeException{
    private static final long serialVersionUID = 2663271017306958376L;

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}
