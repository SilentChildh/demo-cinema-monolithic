package com.huanghehua.www.util.interceptor.exception;

/**
 * 未知异常
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class UnknownException extends RuntimeException{

    private static final long serialVersionUID = 5813281055981969210L;

    public UnknownException(String message) {
        super(message);
    }

    public UnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownException(Throwable cause) {
        super(cause);
    }
}
