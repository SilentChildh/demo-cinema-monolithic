package com.huanghehua.www.ioc;

/**
 * bean工厂，用于获取已注册到容器中的bean实例。<br/>
 * <p/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/14
 */
public interface BeanFactory {
    /**
     * 通过对应的bean的class对象获取一个bean实例。<br/>
     *
     * @param beanClass 对应的bean的class对象
     * @return {@link T} 返回一个实例，数据类型为引用类型
     */
    <T> T getBean(Class<?> beanClass);
    /**
     * 通过对应的bean的class对象获取一个bean实例。<br/>
     *
     * @param beanClass      对应的bean的class对象
     * @param interfaceClass 对应的bean接口的class对象
     * @return {@link T} 返回一个实例，数据类型为引用类型
     */
    <T> T getBean(Class<?> beanClass, Class<T> interfaceClass);

    /**
     * 通过全限定包名将对应的元bean注册到容器中。<br/>
     *
     * @param packageName 全限定包名
     */
    void registerMetaBean(String packageName);

}
