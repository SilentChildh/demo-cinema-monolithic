package com.huanghehua.www.cinema.domain.gateway;

/**
 * 注册账户
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public interface AuthenticationGateWay {
    /**
     * 注册
     *
     * @param email 邮箱
     * @param password 密码
     * @return boolean
     */
    boolean doRegister(String email, String password);


    /**
     * 登录，并返回用户id
     *
     * @param email    邮箱
     * @param password 密码
     * @return Long
     */
    Long doLogin(String email, String password);
}
