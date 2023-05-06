package com.huanghehua.www.framework.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标识一个类包含横切逻辑代码。<br/>
 * <p/>
 * 该注解修饰的类将会被扫描，
 * 将类中的横切逻辑方法被放入容器中进行管理，在业务方法需要时被调用。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
}
