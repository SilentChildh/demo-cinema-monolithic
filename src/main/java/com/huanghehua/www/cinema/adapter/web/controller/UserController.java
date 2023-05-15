package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.app.service.UserServiceImpl;
import com.huanghehua.www.cinema.client.api.UserServiceI;
import com.huanghehua.www.cinema.client.dto.UserDTO;
import com.huanghehua.www.cinema.client.dto.command.UserPasswordUpdateCmd;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

/**
 * 用户控制器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
@Bean
@Request("/user")
public class UserController {
    @Reference(UserServiceImpl.class)
    private UserServiceI userService;

    @Request(value = "/setting-password", method = "post")
    public CommonResult<UserDTO> setUserPassword(UserPasswordUpdateCmd userPasswordUpdateCmd) {
        return userService.setUserPassword(userPasswordUpdateCmd);
    }

    @Request(value = "/afford", method = "post")
    public CommonResult<UserDTO> affordOrder(UserPasswordUpdateCmd userPasswordUpdateCmd) {
        // TODO 支付订单
        return userService.setUserPassword(userPasswordUpdateCmd);
    }
}
