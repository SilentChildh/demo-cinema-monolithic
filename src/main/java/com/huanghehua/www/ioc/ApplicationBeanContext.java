package com.huanghehua.www.ioc;

import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;
import com.huanghehua.www.ioc.exception.NotFoundBeanClassException;
import com.huanghehua.www.ioc.manager.InitBeanManager;
import com.huanghehua.www.ioc.manager.RegisterBeanManager;
import com.huanghehua.www.ioc.model.MetaBean;
import com.huanghehua.www.ioc.spi.aop.Interceptable;
import com.huanghehua.www.ioc.spi.aop.InterceptorFactory;
import com.huanghehua.www.ioc.spi.orm.MapperFactory;
import com.huanghehua.www.ioc.util.PrototypeUtil;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 引用数据类型的bean容器。<br/>
 * <p/>
 * 调用者将通过{@code getBean()}方法获取所需的bean实例。<br/>
 * 调用者在调用{@code getBean()}方法前应该先注册元bean，即调用{@code registerMetaBean()}方法.<br/>
 * 该工厂实现类采用延迟创建bean实例的方式，当调用者需要对应bean实例时才进行创建。<br/>
 * 由于延迟加载的原因，容器采用并发Map进行管理。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/14
 */

public class ApplicationBeanContext implements BeanFactory {
    /**
     * bean实例的映射
     * K为bean实例的全限定类名，V为bean实例
     */
    private static final Map<String, Object> BEAN_MAP = new ConcurrentHashMap<>();
    /**
     * 元bean实例的映射
     * K为bean实例的全限定类名，V为bean实例对应的元bean实例
     */
    private static final Map<String, MetaBean> META_BEAN_MAP = new ConcurrentHashMap<>();
    /**
     * 拦截器工厂，应用作用域，事实不可变对象
     */
    private static InterceptorFactory interceptorFactory;
    /**
     * DAO对象工厂，应用作用域，事实不可变对象
     */
    private static MapperFactory mapperFactory;
    static {
        ServiceLoader<InterceptorFactory> interceptorFactoryServiceLoader = ServiceLoader.load(InterceptorFactory.class);
        for (InterceptorFactory resource : interceptorFactoryServiceLoader) {
            interceptorFactory = resource;
        }
        ServiceLoader<MapperFactory> mapperFactoryServiceLoader = ServiceLoader.load(MapperFactory.class);
        for (MapperFactory resource : mapperFactoryServiceLoader) {
            mapperFactory = resource;
        }
    }

    /**
     * 通过对应的bean的class对象获取一个bean实例。<br/>
     *
     * @param beanClass bean类
     * @return {@link T} 返回一个实例，数据类型为引用类型
     */
    public <T> T getBean(Class<?> beanClass) {
        return this.getBean(beanClass, null);
    }
    /**
     * 通过对应的bean的class对象获取一个bean实例。<br/>
     * 通过对应bean的class对象和对应接口的class对象，可以得到拦截器代理bean的实例。
     * 但需要注意的是应该遵循注解的使用规范，否则无法成功拦截。<br/>
     * <p/>
     * beanClass应该是一个标准类，不是一个接口。除非有特别声明，例如被{@link Mapper}修饰。<br/>
     * beanClass应该是对应的bean接口的class对象。<br/>
     * @param interfaceClass 对应的bean接口的class对象
     * @param beanClass      通过对应的bean的class对象获取一个bean实例。
     * @return {@link T} 返回一个实例，数据类型为引用类型
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<?> beanClass, Class<T> interfaceClass) {
        // 特判为null
        if (beanClass == null) {
            LOGGER.log(Level.SEVERE, "BeanClassNotAllowNull");
            throw new NotFoundBeanClassException("BeanClassNotAllowNull");
        }

        // 尝试从beanMap容器中获取已有的bean实例
        String beanName = beanClass.getName();
        if (BEAN_MAP.containsKey(beanName)) {
            if (META_BEAN_MAP.get(beanName).getScope().equals(Bean.BeanScope.PROTOTYPE)) {
                return (T) PrototypeUtil.deepClone(BEAN_MAP.get(beanName));
            }
            return (T) BEAN_MAP.get(beanName);
        }


        Object bean;
        // 如果是Mapper，从映射工厂中获取
        if (isMapper(beanClass)) {
            bean = mapperFactory.getMapper(beanClass);
        }
        // 如果是被拦截器拦截的，从拦截工厂中获取
        else if (isInterceptable(beanClass) && interfaceClass != null) {
            bean = interceptorFactory.getBean(beanClass, interfaceClass);
        }
        // 如果不是以上情况，则直接创建
        else {
            bean = InitBeanManager.newInstance(beanName);
        }


        // 上锁，以免破坏单例bean
        synchronized (BEAN_MAP) {
            if (!BEAN_MAP.containsKey(beanName)) {
                // 放入集合进行管理
                BEAN_MAP.put(beanName, bean);
            }
        }
        // 需要从容器中获取，以免返回的是多创建出来的bean
        return (T) BEAN_MAP.get(beanName);
    }



    /**
     * 通过全限定包名将对应的元bean注册到{@code metaBeanMap}容器中。<br/>
     * <p/>
     * 需要注意的是，注解中的信息将会覆盖掉xml文件中配置的信息。<br/>
     *
     * @param packageName 全限定包名
     */

    @Override
    public void registerMetaBean(String packageName) {
        // 获取meta信息
        Map<String, MetaBean> mapFromXml = RegisterBeanManager.registerMetaBeanFromXml(packageName);
        Map<String, MetaBean> mapFromAnnotation = RegisterBeanManager.registerMetaBeanFromAnnotation(packageName);

        // 注册
        ApplicationBeanContext.META_BEAN_MAP.putAll(mapFromXml);
        ApplicationBeanContext.META_BEAN_MAP.putAll(mapFromAnnotation);
    }

    /**
     * 判断是否被{@link Mapper}修饰。<br/>
     *
     * @param clazz 指定的class对象
     * @return boolean true为是，false为不是
     */
    private boolean isMapper(Class<?> clazz) {
        final Class<Mapper> annotation = Mapper.class;
        // 判断
        return clazz.isAnnotationPresent(annotation);
    }

    /**
     * 判断是否被{@link Interceptable}修饰。<br/>
     *
     * @param clazz 指定的class对象
     * @return boolean true为是，false为不是
     */
    private boolean isInterceptable(Class<?> clazz) {
        final Class<Interceptable> annotation = Interceptable.class;
        // 判断
        return clazz.isAnnotationPresent(annotation);
    }

    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.ioc.ApplicationBeanContext");
}

