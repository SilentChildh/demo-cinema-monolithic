package com.huanghehua.www.dispatch.validation.exception;

/**
 * 不属于限定范围之内时抛出该异常。
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class RangeException extends RuntimeException{
    private static final long serialVersionUID = -6775831493014124089L;

    public RangeException(String message) {
        super(message);
    }

    public RangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RangeException(Throwable cause) {
        super(cause);
    }
}
