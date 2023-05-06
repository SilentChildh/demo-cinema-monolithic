package com.huanghehua.www.dispatch.validation.annotation;

import com.huanghehua.www.dispatch.validation.exception.BlankException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制字段不能为null或者是空串，
 * 否则抛出{@link BlankException}<br/>
 * 需要注意的是该注解只对字符串生效。<br/>
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {
}
