package com.huanghehua.www.framework.dispatch.exception;

/**
 * 反射异常，用于包装关于框架因反射而抛出的异常。
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/31
 */
public class ReflectionException extends RuntimeException {
    private static final long serialVersionUID = 6843318560151783368L;
    public ReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionException(Throwable cause) {
        super(cause);
    }


}
