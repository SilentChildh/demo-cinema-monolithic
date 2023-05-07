package com.huanghehua.www.cinema.authentication.domain.gateway;

/**
 * 注册账户
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public interface RegisterGateWay {
    /**
     * 注册
     *
     * @param email 邮箱
     * @param password 密码
     * @return boolean
     */
    boolean doRegister(String email, String password);
}