package com.huanghehua.www.cinema.client.api;

import com.huanghehua.www.cinema.client.dto.JwtSignatureDTO;
import com.huanghehua.www.cinema.client.dto.command.UserLoginCmd;
import com.huanghehua.www.common.CommonResult;

/**
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public interface AuthenticationServiceI {
    /**
     * 登录，返回一个含有JWT的数字签名
     *
     * @param userLoginCmd 用户登录cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<JwtSignatureDTO> login(UserLoginCmd userLoginCmd);
    /**
     * 注册，返回一个含有用户信息的结果
     *
     * @param email    邮箱
     * @param password 密码
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> register(String email, String password);
}
