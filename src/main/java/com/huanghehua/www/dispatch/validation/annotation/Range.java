package com.huanghehua.www.dispatch.validation.annotation;

import com.huanghehua.www.dispatch.validation.exception.RangeException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制数值类型的数值范围，否则抛出{@link RangeException}
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {
    int min();
    int max();
}
