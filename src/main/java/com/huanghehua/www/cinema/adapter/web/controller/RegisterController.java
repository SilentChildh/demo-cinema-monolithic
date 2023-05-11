package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.client.api.RegisterServiceI;
import com.huanghehua.www.cinema.app.service.RegisterServiceImpl;
import com.huanghehua.www.cinema.client.dto.UserDTO;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

/**
 * 注册控制器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
@Request("/authentication")
public class RegisterController {
    @Reference(RegisterServiceImpl.class)
    private RegisterServiceI registerServiceIExecutor;

    /**
     * 获取请求中携带的用户信息，进行登录操作，成功则返回带有JWT的信息，否则返回错误提示信息。
     *
     * @param userDto 用户请求数据
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/register", method = "post")
    public CommonResult<?> register(UserDTO userDto) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        return registerServiceIExecutor.register(email, password);
    }
}
