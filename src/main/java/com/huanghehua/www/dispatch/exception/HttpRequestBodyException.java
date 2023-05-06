package com.huanghehua.www.dispatch.exception;

/**
 * http请求体异常
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/02
 */
public class HttpRequestBodyException extends RuntimeException{

    private static final long serialVersionUID = -2227484335379085378L;

    public HttpRequestBodyException(String message) {
        super(message);
    }

    public HttpRequestBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestBodyException(Throwable cause) {
        super(cause);
    }
}
