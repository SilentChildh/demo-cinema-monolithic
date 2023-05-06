package com.huanghehua.www.dispatch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求参数映射，用于将QueryString上的键值对映射到controller方法的参数上
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/02
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    String value();
}
