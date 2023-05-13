package com.huanghehua.www.cinema.app.service;

import com.huanghehua.www.cinema.app.executor.command.UserPasswordUpdateCmdExe;
import com.huanghehua.www.cinema.client.api.UserServiceI;
import com.huanghehua.www.cinema.client.dto.UserDTO;
import com.huanghehua.www.cinema.client.dto.command.UserPasswordUpdateCmd;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 用户服务实现类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
@Bean
@Interceptable
public class UserServiceImpl implements UserServiceI {
    @Reference
    private UserPasswordUpdateCmdExe userPasswordUpdateCmdExe;

    @Override
    public CommonResult<UserDTO> setUserPassword(UserPasswordUpdateCmd userPasswordUpdateCmd) {
        return userPasswordUpdateCmdExe.execute(userPasswordUpdateCmd);
    }
}
