package com.huanghehua.www.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用于字段和方法上，字段上可以通过该注解注入简单的值，方法只能作用于set方法之上，并对对应的字段进行赋值.<br/>
 * <p/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/16
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    // 为字段注入的值
    String value();
}
