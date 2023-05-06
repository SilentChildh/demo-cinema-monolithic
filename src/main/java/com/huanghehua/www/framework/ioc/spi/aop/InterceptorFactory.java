package com.huanghehua.www.framework.ioc.spi.aop;

/**
 * 拦截器工厂，用于代理被拦截的业务实现类，使每次调用业务方法时，都会经过指定的横切逻辑代码。<br/>
 * <p/>
 * 拦截器应该属于bean实例，即被{@link com.huanghehua.www.ioc.annotation.Bean}修饰，
 * 拦截器应该拦截的类应该可拦截，即被{@link Interceptable}修饰
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/11
 */
public interface InterceptorFactory {

    /**
     * 核心方法，用于获取代理类。<br/>
     * <p/>
     *
     * @param beanClass      bean实例的class对象
     * @param interfaceClass 通过对应的bean的class对象获取一个bean实例
     * @return {@link T} 返回一个代理实例
     */
    <T> T getBean(Class<?> beanClass, Class<T> interfaceClass);
}
