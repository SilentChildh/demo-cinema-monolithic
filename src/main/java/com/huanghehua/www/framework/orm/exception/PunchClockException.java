package com.huanghehua.www.framework.orm.exception;

/**
 * 打卡异常
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/24
 */
public class PunchClockException extends RuntimeException {
    private static final long serialVersionUID = 8444205026278943425L;

    public PunchClockException() {
    }

    public PunchClockException(String message) {
        super(message);
    }
}
