package com.huanghehua.www.orm.exception;

/**
 * 无接口异常，当试图获取对应类接口而无指定接口时抛出该异常
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/31
 */
public class NoSuchInterfaceException extends RuntimeException{
    private static final long serialVersionUID = 894806352341695581L;

    public NoSuchInterfaceException(String message) {
        super(message);
    }

    public NoSuchInterfaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchInterfaceException(Throwable cause) {
        super(cause);
    }
}
