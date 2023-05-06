package com.huanghehua.www.framework.datasource.exception;

/**
 * 中断异常，一般用于阻塞被中断、超时使线程中断时抛出
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/31
 */
public class InterruptException extends RuntimeException{
    private static final long serialVersionUID = -8996506956736482591L;

    public InterruptException(String message) {
        super(message);
    }

    public InterruptException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterruptException(Throwable cause) {
        super(cause);
    }
}
