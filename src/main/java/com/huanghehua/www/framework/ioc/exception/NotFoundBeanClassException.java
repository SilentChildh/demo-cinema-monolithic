package com.huanghehua.www.framework.ioc.exception;

/**
 * 没有找到bean的类对象异常，一般在注册bean对象时抛出异常
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/31
 */
public class NotFoundBeanClassException extends RuntimeException{
    private static final long serialVersionUID = 8692905078744478217L;

    public NotFoundBeanClassException(String message) {
        super(message);
    }

    public NotFoundBeanClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundBeanClassException(Throwable cause) {
        super(cause);
    }
}
