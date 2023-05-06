package com.huanghehua.www.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于字段或者set方法上，用于注入对其他bean实例的引用。<br/>
 * <p/>
 * 注解中唯一的{@code value}值可以隐式地定位到字段所引用的bean的位置，当然您也可以显式地填写上所要引用的类型。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/16
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Reference {
    Class<?> value() default Class.class;
}
