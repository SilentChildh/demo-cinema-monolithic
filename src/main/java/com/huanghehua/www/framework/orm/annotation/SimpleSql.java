package com.huanghehua.www.framework.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记一个dao方法将使用sql构建器的sql语句
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleSql {
    Class<?> provider();
    String method();
    Class<?> resultType();
}
