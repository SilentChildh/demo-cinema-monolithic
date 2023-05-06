package com.huanghehua.www.framework.dispatch.handler;

import com.huanghehua.www.dispatch.exception.ReflectionException;
import com.huanghehua.www.dispatch.model.VerifyServiceMethodParam;
import com.huanghehua.www.dispatch.validation.SimpleValidator;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 参数验证处理器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/11
 */
public class ParametersVerifyHandler {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.dispatch.com.huanghehua.www.dispatch.handler.ParametersVerifyHandler");

    /**
     * 参数验证处理器，用于对入参进行验证，当不符合要求时，直接抛出异常。
     *
     * @param verifyServiceMethodParam 方法信息
     */
    public static void handle(VerifyServiceMethodParam verifyServiceMethodParam) {
        // 获取业务方法相关信息
        Class<?> serviceClass = verifyServiceMethodParam.getServiceClass();
        String methodName = verifyServiceMethodParam.getMethodName();
        Class<?>[] args = verifyServiceMethodParam.getArgs();
        Object[] value = verifyServiceMethodParam.getValue();

        try {
            // 获取业务方法及其参数
            Method serviceMethod = serviceClass.getDeclaredMethod(methodName, args);
            Parameter[] parameters = serviceMethod.getParameters();
            // 遍历验证
            for (int i = 0; i < parameters.length; i++) {
                SimpleValidator.verify(parameters[i], value[i]);
            }

        } catch (NoSuchMethodException e) {
            LOGGER.log(Level.SEVERE, "找不到需要验证的业务方法", e);
            throw new ReflectionException("找不到需要验证的业务方法", e);
        }

        // 无任何异常抛出，则验证成功
        LOGGER.log(Level.INFO, "Request parameters com.huanghehua.www.dispatch.validation result : true");
    }
}
