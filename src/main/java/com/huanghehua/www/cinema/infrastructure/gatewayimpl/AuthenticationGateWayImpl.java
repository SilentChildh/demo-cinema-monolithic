package com.huanghehua.www.cinema.infrastructure.gatewayimpl;

import com.huanghehua.www.cinema.domain.gateway.AuthenticationGateWay;
import com.huanghehua.www.cinema.infrastructure.data.UserPO;
import com.huanghehua.www.cinema.infrastructure.mapper.UserMapper;
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
public class AuthenticationGateWayImpl implements AuthenticationGateWay {
    @Reference
    private UserMapper userMapper;
    @Override
    public boolean doRegister(String email, String password) {

        return userMapper.insertUser(email, password) > 0;
    }

    @Override
    public boolean doLogin(String email, String password) {
        UserPO userPo = userMapper.selectSingleUserByEmail(email, password);

        return userPo != null;
    }
}
