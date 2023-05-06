package com.huanghehua.www.authentication.web.controller;

import com.huanghehua.rpc.proxy.Stub;
import com.huanghehua.www.authentication.api.Login;
import com.huanghehua.www.authentication.executor.LoginExecutor;
import com.huanghehua.www.authentication.web.data.UserVO;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.ioc.annotation.Bean;


/**
 * 登录控制器
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
@Bean
@Request("/authentication")
public class LoginController {
    private final Login loginExecutor = Stub.getStub(Login.class, LoginExecutor.class);

    /**
     * 获取请求中携带的用户信息，进行登录操作，成功则返回带有JWT的信息，否则返回错误提示信息。
     *
     * @param userVo 用户请求数据
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/login", method = "post")
    public CommonResult<?> login(UserVO userVo) {
        String email = userVo.getEmail();
        String password = userVo.getPassword();
        return loginExecutor.login(email, password);
    }

}
