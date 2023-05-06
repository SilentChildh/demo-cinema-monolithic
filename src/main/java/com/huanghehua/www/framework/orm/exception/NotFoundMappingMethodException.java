package com.huanghehua.www.framework.orm.exception;

/**
 * 没有发现Mapper与DAO接口对应映射方法的异常。一般在进行参数处理时抛出
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/31
 */
public class NotFoundMappingMethodException extends RuntimeException{
    private static final long serialVersionUID = 2688728625482084148L;

    public NotFoundMappingMethodException(String message) {
        super(message);
    }

    public NotFoundMappingMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundMappingMethodException(Throwable cause) {
        super(cause);
    }
}
