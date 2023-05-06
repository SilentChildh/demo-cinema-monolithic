package com.huanghehua.www.dispatch.validation.exception;

/**
 * 当字符串为空串或者为null时抛出该异常
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class BlankException extends RuntimeException{
    private static final long serialVersionUID = 5068672756883632783L;

    public BlankException(String message) {
        super(message);
    }

    public BlankException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlankException(Throwable cause) {
        super(cause);
    }
}
