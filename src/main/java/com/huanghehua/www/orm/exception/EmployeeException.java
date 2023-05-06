package com.huanghehua.www.orm.exception;

/**
 * 员工异常
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/24
 */
public class EmployeeException extends RuntimeException{
    private static final long serialVersionUID = -5558101574344935114L;

    public EmployeeException() {
    }

    public EmployeeException(String message) {
        super(message);
    }
}
