package com.huanghehua.www.cinema.adapter.web.controller;


import com.huanghehua.www.cinema.app.service.LoginServiceImpl;
import com.huanghehua.www.cinema.client.api.LoginServiceI;
import com.huanghehua.www.cinema.client.dto.UserDTO;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;


/**
 * 登录控制器
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
@Bean
@Request("/authentication")
public class LoginController {
    @Reference(LoginServiceImpl.class)
    private LoginServiceI loginServiceIExecutor;

    /**
     * 获取请求中携带的用户信息，进行登录操作，成功则返回带有JWT的信息，否则返回错误提示信息。
     *
     * @param userDto 用户请求数据
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/login", method = "post")
    public CommonResult<?> login(UserDTO userDto) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        return loginServiceIExecutor.login(email, password);
    }

}
