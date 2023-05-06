package com.huanghehua.www.framework.dispatch.exception;

/**
 * http查询参数异常，一般在键值对中的Value不存在时抛出
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class HttpQueryParameterException extends RuntimeException{
    private static final long serialVersionUID = -8535452573307379676L;

    public HttpQueryParameterException(String message) {
        super(message);
    }

    public HttpQueryParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpQueryParameterException(Throwable cause) {
        super(cause);
    }
}
