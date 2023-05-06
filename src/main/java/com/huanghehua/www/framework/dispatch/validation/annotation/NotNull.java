package com.huanghehua.www.framework.dispatch.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制字段不能为null，否则抛出异常{@link NullPointerException}
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
}
