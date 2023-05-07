package com.huanghehua.www.cinema.authentication.infrastructure.gatewayimpl;

import com.huanghehua.www.cinema.authentication.domain.gateway.LoginGateWay;
import com.huanghehua.www.cinema.authentication.infrastructure.data.UserPO;
import com.huanghehua.www.cinema.authentication.infrastructure.mapper.UserMapper;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

/**
 * 登录实现类
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
@Bean
public class LoginGateWayImpl implements LoginGateWay {
    @Reference
    private UserMapper userMapper;


    @Override
    public boolean doLogin(String email, String password) {
        UserPO userPo = userMapper.selectSingleUserByEmail(email, password);

        return userPo != null;
    }
}