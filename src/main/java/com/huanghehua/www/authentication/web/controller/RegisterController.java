package com.huanghehua.www.authentication.web.controller;

import com.huanghehua.www.authentication.app.executor.RegisterExecutor;
import com.huanghehua.www.authentication.client.api.Register;
import com.huanghehua.www.authentication.web.data.UserVO;
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
    @Reference(RegisterExecutor.class)
    private Register registerExecutor;

    /**
     * 获取请求中携带的用户信息，进行登录操作，成功则返回带有JWT的信息，否则返回错误提示信息。
     *
     * @param userVo 用户请求数据
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/register", method = "post")
    public CommonResult<?> register(UserVO userVo) {
        String email = userVo.getEmail();
        String password = userVo.getPassword();
        return registerExecutor.register(email, password);
    }
}
