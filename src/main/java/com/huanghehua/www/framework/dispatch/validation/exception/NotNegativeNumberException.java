package com.huanghehua.www.framework.dispatch.validation.exception;

/**
 * 不能为负数异常，一般用于数据类型
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class NotNegativeNumberException extends RuntimeException{
    private static final long serialVersionUID = 5554986666147095198L;

    public NotNegativeNumberException(String message) {
        super(message);
    }

    public NotNegativeNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotNegativeNumberException(Throwable cause) {
        super(cause);
    }
}
