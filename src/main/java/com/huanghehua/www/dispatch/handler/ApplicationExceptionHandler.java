package com.huanghehua.www.dispatch.handler;

import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.common.State;
import com.huanghehua.www.dispatch.annotation.ExceptionType;
import com.huanghehua.www.dispatch.exception.HttpQueryParameterException;
import com.huanghehua.www.dispatch.validation.exception.RangeException;
import com.huanghehua.www.dispatch.validation.exception.RegexException;
import com.huanghehua.www.orm.exception.QueryException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 应用程序异常处理器，利用反射完成对所有controller抛出异常的拦截。
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/30
 */
public class ApplicationExceptionHandler {

    /**
     * 查询异常拦截器。<br/>
     *
     * @see QueryException
     * @param e 异常信息
     * @return {@link CommonResult}<{@link ?}>
     */
    @ExceptionType(QueryException.class)
    private CommonResult<?> queryExceptionInterceptor(Throwable e) {
        LOGGER.log(Level.INFO, "用户请求参数错误", e);
        return new CommonResult<>(State.CLIENT_BAD_PARAMETERS, "查询方式有问题，请检查您输入的信息并重试");
    }

    /**
     * 数字格式异常拦截器
     *
     * @param e e
     * @return {@link CommonResult}<{@link ?}>
     */
    @ExceptionType(NumberFormatException.class)
    private CommonResult<?> numberFormatExceptionInterceptor(Throwable e) {
        LOGGER.log(Level.INFO, "用户请求参数错误", e);
        return new CommonResult<>(State.CLIENT_BAD_PARAMETERS,
                "输入数值信息的格式错误，请检查您输入的信息是否是数字并重试");
    }

    /**
     * 数值范围异常拦截器
     *
     * @param e e
     * @return {@link CommonResult}<{@link ?}>
     */
    @ExceptionType(RangeException.class)
    private CommonResult<?> rangeExceptionInterceptor(Throwable e) {
        LOGGER.log(Level.INFO, "用户请求参数的数值范围不符合要求", e);
        return new CommonResult<>(State.CLIENT_BAD_PARAMETERS,
                "请求参数的数值范围不符合要求，请检查您输入的信息是否符合范围要求并重试");
    }

    /**
     * http查询参数异常拦截器
     *
     * @param e e
     * @return {@link CommonResult}<{@link ?}>
     */
    @ExceptionType(HttpQueryParameterException.class)
    private CommonResult<?> httpQueryParameterExceptionInterceptor(Throwable e) {
        LOGGER.log(Level.INFO, "用户请求参数错误", e);
        return new CommonResult<>(State.CLIENT_BAD_PARAMETERS,
                "可能缺少输入相关信息，请检查您输入的信息是否完整并重试");
    }
    @ExceptionType(RegexException.class)
    private CommonResult<?> regexExceptionInterceptor(Throwable e) {
        LOGGER.log(Level.INFO, "用户请求参数错误", e);
        return new CommonResult<>(State.CLIENT_BAD_PARAMETERS,
                "您输入的本文信息可能不符合规范，请检查您输入的信息并重试");
    }
    @ExceptionType(SQLIntegrityConstraintViolationException.class)
    private CommonResult<?> sqlIntegrityConstraintViolationExceptionInterceptor(Throwable e) {
        LOGGER.log(Level.INFO, "用户请求参数错误", e);
        return new CommonResult<>(State.CLIENT_REQUEST_SERVICE_ERROR,
                "您输入的信息已被注册，请重新尝试");
    }


    /**
     * 全局异常处理的核心方法，用于将全局异常分类，然后分发给不同的异常拦截器。<br/>
     *
     * @param supplier 供应商
     * @return {@link CommonResult}<{@link ?}>
     * @throws InvocationTargetException 调用目标异常
     * @throws IllegalAccessException    非法访问异常
     */
    public CommonResult<?> handler(Supplier<CommonResult<?>> supplier)
            throws InvocationTargetException, IllegalAccessException {
        try {
            return supplier.get();
        } catch (Exception e) {
            // 结果异常处理结果
            Object result;
            Class<ApplicationExceptionHandler> clazz = ApplicationExceptionHandler.class;
            Method[] declaredMethods = clazz.getDeclaredMethods();

            // 循环遍历得到异常的源头
            Throwable throwable = e;
            while (throwable.getCause() != null) {
                throwable = throwable.getCause();
            }
            // 获取异常原因的class对象
            Class<? extends Throwable> causeClass = throwable.getClass();
            // 遍历全局异常处理器中的方法，找到对应异常的处理器
            for (Method declaredMethod : declaredMethods) {
                // 判断是否存在注解
                if (declaredMethod.isAnnotationPresent(EXCEPTION_TYPE_CLASS)) {
                    Class<?> value = declaredMethod.getAnnotation(EXCEPTION_TYPE_CLASS).value();
                    // 再次判断异常类型是否对应
                    if (value.equals(causeClass)) {
                        result = declaredMethod.invoke(this, e);
                        return (CommonResult<?>) result;
                    }
                }
            }

            // 如果未找到对应的异常处理器则直接打印严重日志，并返回结果
            LOGGER.log(Level.SEVERE, "服务器内部错误", e);
            return new CommonResult<>(State.SERVER_ERROR, "服务器已崩溃，请联系管理员");
        }
    }

    private static final Class<ExceptionType> EXCEPTION_TYPE_CLASS = ExceptionType.class;
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.controller.ApplicationExceptionController");

}
