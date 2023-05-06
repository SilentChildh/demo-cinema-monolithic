package com.huanghehua.www.dispatch.validation.annotation;

import com.huanghehua.www.dispatch.validation.exception.EmptyException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制字符串长度不为0
 * 限制数组中的元素不存在null,
 * 限制集合的元素个数不为0
 * 否则抛出{@link EmptyException}<br/>
 * <p/>
 * 该注解只能用于验证非 null 对象<br/>
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {
}
