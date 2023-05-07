package com.huanghehua.www.authentication.infrastructure.gatewayimpl;

import com.huanghehua.www.authentication.domain.gateway.RegisterGateWay;
import com.huanghehua.www.authentication.infrastructure.mapper.UserMapper;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

/**
 * 注册实现类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
@Bean
public class RegisterGateWayImpl implements RegisterGateWay {
    @Reference
    private UserMapper userMapper;
    @Override
    public boolean doRegister(String email, String password) {

        return userMapper.insertUser(email, password) > 0;
    }
}
