package com.huanghehua.www.framework.dispatch.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求路径的映射。作用于类和方法上。<br/>
 * 也可以标记http请求方法类型，默认为GET类型，填写类型时不区分大小写
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/30
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Request {
    /**
     * 路径映射
     *
     * @return {@link String}
     */
    String value();

    /**
     * 请求方法,默认为GET类型，不区分大小写
     *
     * @return {@link String}
     */
    String method() default "GET";
}
