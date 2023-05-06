package com.huanghehua.www.framework.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记一个insert方法需要返回主键
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenericKey {
}
