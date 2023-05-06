package com.huanghehua.www.datasource.exception;

/**
 * 超时异常.<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/12
 */
public class TimedOutException extends RuntimeException{

    public TimedOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimedOutException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = 4243098620529872112L;

    public TimedOutException(String message) {
        super(message);
    }
}
