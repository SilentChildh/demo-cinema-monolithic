package com.huanghehua.www.cinema.domain.gateway;

/**
 * 登录
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public interface LoginGateWay {

    /**
     * 登录
     *
     * @param email 邮箱
     * @param password 密码
     * @return boolean
     */
    boolean doLogin(String email, String password);
}
