package com.huanghehua.www.framework.dispatch.validation.annotation;

import com.huanghehua.www.dispatch.validation.SimpleValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证类型，用于标识{@link SimpleValidator}中的验证方法验证的是哪一个注解类型
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyType {
    Class<?> value();
}
