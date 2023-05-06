package com.huanghehua.www.authentication.app.executor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.huanghehua.www.authentication.client.api.Login;
import com.huanghehua.www.authentication.client.dto.SignatureDTO;
import com.huanghehua.www.authentication.domain.gateway.LoginGateWay;
import com.huanghehua.www.authentication.infrastructure.gatewayimpl.LoginGateWayImpl;
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
public class LoginExecutor implements Login {

    /**
     * 登录domain
     */
    @Reference(LoginGateWayImpl.class)
    private LoginGateWay loginGateWay;

    /**
     * 一天的间隔时间
     */
    private final Period interval = Period.ofDays(1);

    @Override
    public CommonResult<?> login(@RegexPattern(".*@.*") String email,
                                            @Size(min = 6, max = 18) String password) {

        // 参数检验
        VerifyServiceMethodParam verifyServiceMethodParam = new VerifyServiceMethodParam(LoginExecutor.class,
                "login",
                new Class<?>[]{String.class, String.class}, new Object[]{email, password});
        ParametersVerifyHandler.handle(verifyServiceMethodParam);

        // 执行登录业务
        boolean doLogin = loginGateWay.doLogin(email, password);

        // 登录失败，直接返回
        if (!doLogin) {
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
        SignatureDTO signatureDTO = new SignatureDTO(jwt);
        return CommonResult.operateSuccess(signatureDTO);

    }
}
