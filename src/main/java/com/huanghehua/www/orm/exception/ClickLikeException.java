package com.huanghehua.www.orm.exception;

/**
 * 点赞业务异常类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/24
 */
public class ClickLikeException extends RuntimeException{
    private static final long serialVersionUID = -4071035933239581199L;

    public ClickLikeException() {
    }

    public ClickLikeException(String message) {
        super(message);
    }
}
