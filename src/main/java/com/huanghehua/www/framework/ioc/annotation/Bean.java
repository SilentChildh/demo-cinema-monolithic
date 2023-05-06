package com.huanghehua.www.framework.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于表示一个类将作为一个bean实例被IoC容器管理。<br/>
 * <p/>
 * 对于该注解的{@code value}应该填写全限定类名。<br/>
 * 当然您也可以不写，此时默认定位到被修饰类的位置。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
    /**
     * 全限定类名值
     * @return
     */
    String value() default "";

    BeanScope scope() default BeanScope.SINGLETON;

    enum BeanScope {
        /**
         * 单例模式形式返回bean
         */
        SINGLETON,
        /**
         * 原型模式形式返回bean
         */
        PROTOTYPE;
    }
}
