package com.huanghehua.www.dispatch.validation.exception;

/**
 * 限制字符串长度不为0
 * 限制数组中的元素不存在null,
 * 限制集合的元素个数不为0
 * 否则抛出该异常<br/>
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class EmptyException extends RuntimeException{
    private static final long serialVersionUID = 9153515830001229251L;

    public EmptyException(String message) {
        super(message);
    }

    public EmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyException(Throwable cause) {
        super(cause);
    }
}
