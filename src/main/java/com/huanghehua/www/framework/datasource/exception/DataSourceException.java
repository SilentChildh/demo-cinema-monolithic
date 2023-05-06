package com.huanghehua.www.framework.datasource.exception;

/**
 * 数据库异常
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/31
 */
public class DataSourceException extends RuntimeException{
    private static final long serialVersionUID = -3448232911633809535L;
    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceException(Throwable cause) {
        super(cause);
    }


}
