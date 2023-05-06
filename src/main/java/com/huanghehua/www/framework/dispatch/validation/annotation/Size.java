package com.huanghehua.www.framework.dispatch.validation.annotation;

import com.huanghehua.www.dispatch.validation.exception.NotNegativeNumberException;
import com.huanghehua.www.dispatch.validation.exception.RangeException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制字符串、数组、集合的长度范围，否则抛出{@link RangeException}<br/>
 * 特别地，当使用该注解并将最小值设置为负数时，将抛出{@link NotNegativeNumberException}
 *
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {
    int min();
    int max();
}
