package com.huanghehua.www.framework.dispatch.validation.exception;

/**
 * 当正则表达式无法匹配时抛出异常
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class RegexException extends RuntimeException{
    private static final long serialVersionUID = 1807313229307381986L;

    public RegexException(String message) {
        super(message);
    }

    public RegexException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegexException(Throwable cause) {
        super(cause);
    }
}
