package com.huanghehua.www.framework.orm.exception;

import com.huanghehua.www.orm.SqlSession;

/**
 * 查询异常，用于{@link SqlSession}
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/26
 */
public class QueryException extends RuntimeException{
    private static final long serialVersionUID = 1599891442630130244L;

    public QueryException() {
        super();
    }

    public QueryException(String message) {
        super(message);
    }

    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
