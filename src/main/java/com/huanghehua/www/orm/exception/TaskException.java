package com.huanghehua.www.orm.exception;

/**
 * 任务异常类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/24
 */
public class TaskException extends RuntimeException{
    private static final long serialVersionUID = 8576971468005488228L;

    public TaskException() {
    }

    public TaskException(String message) {
        super(message);
    }
}
