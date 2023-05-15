package com.huanghehua.www.cinema.app.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.huanghehua.www.cinema.client.dto.command.UserLoginCmd;
import com.huanghehua.www.cinema.client.api.AuthenticationServiceI;
import com.huanghehua.www.cinema.client.dto.JwtSignatureDTO;
import com.huanghehua.www.cinema.client.dto.UserDTO;
import com.huanghehua.www.cinema.domain.gateway.AuthenticationGateWay;
import com.huanghehua.www.cinema.infrastructure.gatewayimpl.AuthenticationGateWayImpl;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.handler.ParametersVerifyHandler;
import com.huanghehua.www.dispatch.model.VerifyServiceMethodParam;
import com.huanghehua.www.dispatch.validation.annotation.RegexPattern;
import com.huanghehua.www.dispatch.validation.annotation.Size;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

import java.time.Instant;
import java.time.Period;


/**
 * 登录业务执行器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
@Bean
@Interceptable
public class AuthenticationServiceImpl implements AuthenticationServiceI {
    @Reference(AuthenticationGateWayImpl.class)
    private AuthenticationGateWay authenticationGateWay;

    @Override
    public CommonResult<?> login(UserLoginCmd userLoginCmd) {

        String email = userLoginCmd.getEmail();
        String password = userLoginCmd.getPassword();

        // 一天的间隔时间
        final Period interval = Period.ofDays(1);

        // 执行登录业务，并获取用户id
        Long userId = authenticationGateWay.doLogin(email, password);

        // 登录失败，直接返回
        if (userId == null) {
            return CommonResult.operateFail("login fail! please retry...");
        }

        // 获取生效时间
        Instant now = Instant.now();
        // 获取JWT
        JWTCreator.Builder builder = JWT.create()
                .withIssuer("www.huanghehua.com")
                .withExpiresAt(now.plus(interval))
                .withSubject("authentication enter cinema system")
                .withAudience("user", "admin")
                .withNotBefore(now)
                .withIssuedAt(now);
        String secretKey = "?????????????";
        String jwt = builder.sign(Algorithm.HMAC256(secretKey));

        // JWT封装为DTO，并返回
        JwtSignatureDTO jwtSignatureDTO = new JwtSignatureDTO(userId, jwt);
        return CommonResult.operateSuccess(jwtSignatureDTO);

    }


    @Override
    public CommonResult<?> register(@RegexPattern(".*@.*") String email,
                                    @Size(min = 6, max = 18) String password) {

        // 参数检验
        VerifyServiceMethodParam verifyServiceMethodParam =
                new VerifyServiceMethodParam(AuthenticationServiceImpl.class, "register",
                new Class<?>[]{String.class, String.class}, new Object[]{email, password});
        ParametersVerifyHandler.handle(verifyServiceMethodParam);

        // 执行注册业务
        boolean doRegister = authenticationGateWay.doRegister(email, password);

        // 注册失败，直接返回
        if (!doRegister) {
            return CommonResult.operateFail("login fail! please retry...");
        }

        return CommonResult.operateSuccess(new UserDTO(email, password, true));

    }
}
