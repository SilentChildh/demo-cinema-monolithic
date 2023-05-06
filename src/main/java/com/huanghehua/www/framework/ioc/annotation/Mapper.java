package com.huanghehua.www.framework.ioc.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识一个接口是用于操作数据库。<br/>
 * <p/>
 * 被该注释修饰，且同时被{@link Bean}注解修饰时，
 * 此时会自动的调用{@link MapperFactory}创建一个接口的实现类。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapper {
}
