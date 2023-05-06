package com.huanghehua.www.ioc.manager;


import com.huanghehua.www.ioc.ApplicationBeanContext;
import com.huanghehua.www.ioc.exception.ReflectionException;
import com.huanghehua.www.ioc.model.MetaBean;
import com.huanghehua.www.ioc.util.ConvertUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 初始话bean实例的管理器，用于创建一个bean实例。<br/>
 * <p/>
 * 该类将拥有对元bean容器和bean实例容器的可读权限，主要用于辅助创建bean实例。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/18
 */
public class InitBeanManager {
    private static final Logger LOGGER =
            Logger.getLogger("com.huanghehua.www.framework.ioc.managet.InitBeanManager");

    /**
     * 从{@link ApplicationBeanContext}中获取的bean实例的不可修改视图映射，
     * K为bean实例的全限定类名，V为bean实例
     */
    private static Map<String, Object> BEAN_MAP;
    /**
     * 从{@link ApplicationBeanContext}中获取的元bean实例的不可修改视图映射
     * K为bean实例的全限定类名，V为bean实例对应的元bean实例
     */
    private static Map<String, MetaBean> META_BEAN_MAP;

    //从{@link ApplicationBeanContext}中得到对两个Map映射的可读权限
    static {
        Class<ApplicationBeanContext> beanFactoryClass = ApplicationBeanContext.class;
        final String beanMap = "BEAN_MAP";
        final String metaBeanMap = "META_BEAN_MAP";
        try {
            Field beanFactoryMap = beanFactoryClass.getDeclaredField(beanMap);
            Field beanFactoryMetaBeanMap = beanFactoryClass.getDeclaredField(metaBeanMap);
            beanFactoryMap.setAccessible(true);
            InitBeanManager.BEAN_MAP = Collections.unmodifiableMap((Map<String, Object>) beanFactoryMap.get(beanFactoryClass));

            beanFactoryMetaBeanMap.setAccessible(true);
            InitBeanManager.META_BEAN_MAP = Collections.unmodifiableMap((Map<String, MetaBean>) beanFactoryMetaBeanMap.get(beanFactoryClass));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Convert to view  fail.", e);
            throw new ReflectionException("Convert to view  fail.", e);
        }
    }


    /**
     * 通过全限定类名创建bean实例。<br/>
     * <p/>
     * 调用该方法将会在metaBeanMap中寻找对应的bean信息，
     * 并通过反射创建、注入值，最后将bean放入beanMap中，且返回一个bean实例给调用者。<br/>
     *
     * @param beanName bean的全限定类名
     * @return {@link Object} 返回一个bean实例
     */
    public static Object newInstance(String beanName) {
        /*创建bean*/
        // bean实例的class对象，并创建一个bean
        Class<?> beanClass;
        Object bean;
        try {
            beanClass = Class.forName(beanName);
            bean = beanClass.newInstance();

        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "beanClassNotFound", e);
            throw new ReflectionException("beanClassNotFound", e);

        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "实例化bean Fail", e);
            throw new ReflectionException("实例化bean Fail", e);
        }

        /*接下来开始对bean赋值*/
        // 首先获取元bean
        MetaBean metaBean = META_BEAN_MAP.get(beanName);
        // 开始注入值
        inject(bean, beanClass, metaBean);

        // 注入完成，重置状态
        metaBean.setInjecting(false);
        return bean;

    }

    /**
     * 为bean实例注入依赖，并返回一个完成注入的bean实例。<br/>
     * <p/>
     *
     * @param bean      bean实例
     * @param beanClass bean实例的class对象
     * @param metaBean  bean实例的元信息
     */
    private static void inject(Object bean, Class<?> beanClass, MetaBean metaBean) {
        // 更新状态为正在注入
        metaBean.setInjecting(true);
        // 获取所有字段
        Field[] beanFields = beanClass.getDeclaredFields();

        // 循环赋值
        Arrays.stream(beanFields).forEach(field -> {
            try {

                injectSimpleValue(bean, metaBean, field);
                injectReference(bean, metaBean, field);

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.log(Level.SEVERE, "bean实例赋值Fail", e);
                throw new ReflectionException("bean实例赋值Fail", e);
            }

        });
    }

    /**
     * 注入简单值
     *
     * @param bean     bean实例
     * @param metaBean bean实例的class对象
     * @param field    需要注入值的字段
     * @throws InvocationTargetException 调用目标异常
     * @throws NoSuchMethodException     没有这样方法异常
     * @throws IllegalAccessException    非法访问异常
     */
    private static void injectSimpleValue(Object bean, MetaBean metaBean, Field field) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // 获取字段与简单值映射集合
        Map<String, String> properties = metaBean.getProperties();

        // 字段类型的类对象
        Class<?> fieldTypeClass = field.getType();
        // 字段名
        String fieldName = field.getName();

        // 从配置信息中获取需要注入的属性值
        String fieldValue = properties.get(fieldName);

        // 值为null时，不进行赋值
        if (fieldValue == null) {
            return;
        }

        /*
         * 值不为null时，进行简单值的注入
         */

        // 实际应该注入的依赖
        Object actual;
        // 获取对应字段类型的属性值
        actual = ConvertUtils.convertToWrapperType(fieldValue, fieldTypeClass);

        // 对字段注入依赖
        field.setAccessible(true);
        field.set(bean, actual);
    }

    /**
     * 注入引用值。<br/>
     * <p/>
     * 需要注意的是，当注入依赖为引用类型时，所依赖的bean必须通过{@link ApplicationBeanContext}获取，
     * 不能直接从本类中{@code init()}获取。因为关于bean实例的管理是在工厂内，本类只是一个生成bean的管理器。<br/>
     * 如果不需要注入引用值，需要特判beanMap中该引用是否为null，因为并发Map不允许K为null，继续调用底层会抛出异常。<br/>
     *
     * @param bean     bean实例
     * @param metaBean bean实例的class对象
     * @param field    需要注入值的字段
     * @throws IllegalAccessException 非法访问异常
     */
    private static void injectReference(Object bean, MetaBean metaBean, Field field) throws IllegalAccessException {
        // 获取字段与注入值的映射
        Map<String, Class<?>> references = metaBean.getReferences();

        // 字段名
        String fieldName = field.getName();

        /*
         * 为null时，进行引用注入
         */
        // 实际应该注入的依赖
        Object actual;
        // 得到依赖的类型
        Class<?> refTypeClass = references.get(fieldName);

        // 并发Map需要特判null，否则底层会抛出异常
        if (refTypeClass == null) {
            LOGGER.log(Level.WARNING, "NotFound依赖的类对象");
            // 不存在依赖时，直接跳过
            return;
        }

        // 上锁，防止重复注入
        synchronized (InitBeanManager.class) {
            // 先得到依赖的元bean
            MetaBean referenceMetaBean = META_BEAN_MAP.get(refTypeClass.getName());


            // 如果未被SubImplement注解修饰，且未找到元信息
            if (referenceMetaBean == null) {
                LOGGER.log(Level.WARNING, "NotFound 依赖的元bean");
                return;
            }

            // 如果所依赖的引用处于注入的状态，那么进行注入null，不再尝试获取引用
            if (referenceMetaBean.getInjecting().get()) {
                actual = null;
            }
            // 否则尝试注入
            else {
                // 不为null时，尝试从容器中获取
                Object referenceBean = BEAN_MAP.get(refTypeClass.getName());

                /*
                 * 如果存在该bean实例，则直接跳出进行set赋值
                 * 如果IoC容器中不存在该bean，则创建该bean
                 */
                Class<?> fieldTypeClass = field.getType();

                if (refTypeClass.equals(fieldTypeClass)) {
                    actual = referenceBean != null ? referenceBean :
                            new ApplicationBeanContext().getBean(refTypeClass);
                }
                // 如果不等，说明是依赖倒置的关系
                else {
                    actual = referenceBean != null ? referenceBean :
                            new ApplicationBeanContext().getBean(refTypeClass, fieldTypeClass);
                }

            }

            // 对字段注入依赖
            field.setAccessible(true);
            field.set(bean, actual);
        }
    }

}
