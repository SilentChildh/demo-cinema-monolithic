package com.huanghehua.www.cinema.client.api;

import com.huanghehua.www.common.CommonResult;

/**
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public interface LoginServiceI {
    /**
     * 登录，返回一个含有JWT的数字签名
     *
     * @param email    邮箱
     * @param password 密码
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> login(String email, String password);
}
