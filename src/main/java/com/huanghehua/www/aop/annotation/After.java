package com.huanghehua.www.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 修饰横切逻辑方法，用于某业务方法后调用该横切逻辑方法。<br/>
 * <p/>
 * {@code value}值中应该填写正则表达式，该正则表达式要求能够匹配得到业务方法的全限定方法名。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface After {
    /**
     * 正则表达式， 正则表达式要求能够匹配得到业务方法的全限定方法名
     *
     * @return {@link String}
     */
    String value();
}
