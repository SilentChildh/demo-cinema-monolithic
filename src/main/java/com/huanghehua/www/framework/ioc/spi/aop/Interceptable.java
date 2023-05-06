package com.huanghehua.www.framework.ioc.spi.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于标识一个类中的方法可以被拦截，即插入横切逻辑代码。<br/>
 * <p/>
 * 特别地，该注解应该修饰需要拦截的类的接口，故该类需要实现一个接口。<br/>
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Interceptable {
}
