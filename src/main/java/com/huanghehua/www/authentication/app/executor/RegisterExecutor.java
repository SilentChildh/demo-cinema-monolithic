package com.huanghehua.www.authentication.app.executor;

import com.huanghehua.www.authentication.client.api.Register;
import com.huanghehua.www.authentication.client.dto.UserDTO;
import com.huanghehua.www.authentication.domain.gateway.RegisterGateWay;
import com.huanghehua.www.authentication.infrastructure.gatewayimpl.RegisterGateWayImpl;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.handler.ParametersVerifyHandler;
import com.huanghehua.www.dispatch.model.VerifyServiceMethodParam;
import com.huanghehua.www.dispatch.validation.annotation.RegexPattern;
import com.huanghehua.www.dispatch.validation.annotation.Size;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;


/**
 * 注册业务执行器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
@Bean
@Interceptable
public class RegisterExecutor implements Register {
    /**
     * 注册domain
     */
    @Reference(RegisterGateWayImpl.class)
    private RegisterGateWay registerGateWay;

    @Override
    public CommonResult<?> register(@RegexPattern(".*@.*") String email,
                                 @Size(min = 6, max = 18) String password) {

        // 参数检验
        VerifyServiceMethodParam verifyServiceMethodParam = new VerifyServiceMethodParam(RegisterExecutor.class,
                "register",
                new Class<?>[]{String.class, String.class}, new Object[]{email, password});
        ParametersVerifyHandler.handle(verifyServiceMethodParam);

        // 执行注册业务
        boolean doRegister = registerGateWay.doRegister(email, password);

        // 注册失败，直接返回
        if (!doRegister) {
            return CommonResult.operateFail("login fail! please retry...");
        }

        return CommonResult.operateSuccess(new UserDTO(email, password, true));

    }
}
