package com.huanghehua.www.framework.dispatch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 异常类型，作用于全局异常处理器的方法上。
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionType {
    Class<?> value();
}
