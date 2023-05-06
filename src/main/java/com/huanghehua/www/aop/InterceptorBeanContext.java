package com.huanghehua.www.aop;


import com.huanghehua.www.aop.annotation.After;
import com.huanghehua.www.aop.annotation.Around;
import com.huanghehua.www.aop.annotation.Before;
import com.huanghehua.www.aop.exception.NoSuchInterfaceException;
import com.huanghehua.www.aop.exception.ReflectionException;
import com.huanghehua.www.aop.model.Executor;
import com.huanghehua.www.ioc.ApplicationBeanContext;
import com.huanghehua.www.ioc.manager.InitBeanManager;
import com.huanghehua.www.ioc.spi.aop.InterceptorFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 拦截工厂，用于代理被拦截的业务实现类，使每次调用业务方法时，都会经过指定的横切逻辑代码。<br/>
 * <p/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/18
 */
public class InterceptorBeanContext implements InterceptorFactory {
    /**
     * 横切逻辑代码中被{@link Before}修饰的的Map
     * K为能够匹配业务方法的正则表达式，V为全限定横切方法名
     */
    private static Map<String, String> BEFORE_ADVICES;
    /**
     * 横切逻辑代码中被{@link Around}修饰的的Map
     * K为能够匹配业务方法的正则表达式，V为全限定横切方法名
     */
    private static Map<String, String> AROUND_ADVICES;
    /**
     * 横切逻辑代码中被{@link After}修饰的的Map
     * K为能够匹配业务方法的正则表达式，V为全限定横切方法名
     */
    private static Map<String, String> AFTER_ADVICES;

    /*在初始化一个工厂时，扫描类路径下的所有包，获取切面类并注册*/
    static {
        // TODO: 如何避免硬编码
        AspectManager.registerAspect("com.huanghehua.www");

        InterceptorBeanContext.BEFORE_ADVICES = AspectManager.getBeforeAdvices();
        InterceptorBeanContext.AROUND_ADVICES = AspectManager.getAroundAdvices();
        InterceptorBeanContext.AFTER_ADVICES = AspectManager.getAfterAdvices();
    }


    /**
     * 核心方法，用于获取代理类。<br/>
     * <p/>
     *
     * @param beanClass      bean实例的class对象
     * @param interfaceClass 通过对应的bean的class对象获取一个bean实例
     * @return {@link T} 返回一个代理实例
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<?> beanClass, Class<T> interfaceClass) {
        /*检查是否存在实现了该接口*/
        Class<?>[] interfaces = beanClass.getInterfaces();
        // 如果不存在该接口，则添加到数组上
        if (Arrays.stream(interfaces).noneMatch(element -> element == interfaceClass)) {
            LOGGER.log(Level.SEVERE, "This bean class instance don't has such interface.");
            throw new NoSuchInterfaceException("This bean class instance don't has such interface.");
        }

        String beanName = beanClass.getName();
        // bean实例
        Object bean = InitBeanManager.newInstance(beanName);

        return (T) Proxy.newProxyInstance(beanClass.getClassLoader(), new Class[]{interfaceClass},
                (proxy, method, args) -> this.intercept(bean, method, args));
    }

    /**
     * 拦截业务方法
     *
     * @param bean   业务方法所属的类
     * @param method 业务方法
     * @param args   传入业务方法的实参
     * @return {@link Object}
     * @throws InvocationTargetException 调用目标异常
     * @throws IllegalAccessException    非法访问异常
     */
    private Object intercept(Object bean, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        // 将参数包装为执行器bean对象
        Executor executor = new Executor(bean, method, args);
        // 接收业务方法返回结果
        AtomicReference<Object> result = new AtomicReference<>();

        /*首先拼接得到业务方法的全限定方法名*/
        Class<?> declaringClass = method.getDeclaringClass();
        String className = declaringClass.getName();
        String methodName = method.getName();
        // 业务方法的全限定方法名
        String baseMethodName = className + '.' + methodName;

        // 获取before横切方法
        Map<Method, Object> beforeAdviceMethods = this.getAdviceMethods(baseMethodName, BEFORE_ADVICES);
        // 获取around横切方法
        Map<Method, Object> aroundAdviceMethods = this.getAdviceMethods(baseMethodName, AROUND_ADVICES);
        // 获取after横切方法
        Map<Method, Object> afterAdviceMethods = this.getAdviceMethods(baseMethodName, AFTER_ADVICES);
        Map<Method, Object>[] advices =
                new Map[]{beforeAdviceMethods, aroundAdviceMethods, afterAdviceMethods};

        // 遍历执行横切代码
        for (Map<Method, Object> advice : advices) {
            advice.forEach((adviceMethod, adviceObject) -> {
                try {
                    // 接收结果
                    Object res;
                    // 交给横切逻辑方法执行
                    if (adviceMethod.getParameters().length == 0) {
                        res = adviceMethod.invoke(adviceObject, new Executor());
                    }
                    else {
                        res = adviceMethod.invoke(adviceObject, executor);
                    }

                    // 如果是around横切
                    if (advice == aroundAdviceMethods) {
                        result.set(res);
                    }

                } catch (IllegalAccessException | InvocationTargetException e) {
                    LOGGER.log(Level.SEVERE, "Advice-Interceptor don't work.", e);
                    throw new ReflectionException("Advice-Interceptor don't work.", e);
                }
            });

            // 如果around横切不存在
            if (advice == aroundAdviceMethods && aroundAdviceMethods.size() == 0) {
                result.set(method.invoke(bean, args));
            }
        }

        return result.get();
    }


    /**
     * 用于获取被注解{@link Before}、{@link After}、{@link Around}修饰的横切逻辑方法。<br/>
     * <p/>
     * 当参数{@code around} 为true时，表示获取的是被{@link Around}修饰的横切方法，
     * 否则是{@link Before}、{@link After}修饰的横切方法。<br/>
     *
     * @param baseMethodName 全限定方法名
     * @param advices        横切方法集合
     * @return {@link Map}<{@link Method}, {@link Object}> 返回横切方法映射，K为横切方法，V为切面类bean实例
     */
    private Map<Method, Object> getAdviceMethods(String baseMethodName, Map<String, String> advices) {
        // K为bean实例，V为接收匹配到的横切方法
        Map<Method, Object> adviceMethodMap = new HashMap<>(64);
        // 创建工厂，获取切面bean实例
        ApplicationBeanContext beanFactory = new ApplicationBeanContext();

        advices.forEach((regex, logicBaseMethodName) -> {
            // 匹配
            if (baseMethodName.matches(regex)) {
                // 拆分为全限定类名以及方法名
                int splitIndex = logicBaseMethodName.lastIndexOf('.');
                String logicClassName = logicBaseMethodName.substring(0, splitIndex);
                String logicMethodName = logicBaseMethodName.substring(splitIndex + 1);

                // 通过类名加载class对象
                Class<?> aspectClass;
                try {
                    aspectClass = CLASS_LOADER.loadClass(logicClassName);
                } catch (ClassNotFoundException e) {
                    LOGGER.log(Level.SEVERE, "Advice Class Not Found.", e);
                    throw new ReflectionException("Advice Class Not Found.", e);
                }

                // 从bean工厂获取切面bean实例
                Object aspectBean = beanFactory.getBean(aspectClass);

                // 反射获取横切方法
                Class<?> beanClass = aspectBean.getClass();
                try {
                    Method declaredMethod = beanClass.getDeclaredMethod(logicMethodName);
                    // 添加到Map集合中
                    adviceMethodMap.put(declaredMethod, aspectBean);
                } catch (NoSuchMethodException e) {
                    // 当不存在该参数列表的方法时，仅打印日志
                    LOGGER.log(Level.INFO, "No such NonParameters intercept method");

                    // 并且尝试获取另一种（也是最后一种）参数列表的方法
                    try {
                        Method declaredMethod = beanClass.getDeclaredMethod(logicMethodName, Executor.class);
                        // 添加到Map集合中
                        adviceMethodMap.put(declaredMethod, aspectBean);
                    } catch (NoSuchMethodException ex) {
                        // 若此时还不存在，则需要抛出异常
                        LOGGER.log(Level.SEVERE, "No found intercept method", ex);
                        throw new ReflectionException("No found intercept method", ex);
                    }

                }

            }
        });

        return adviceMethodMap;
    }

    private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();
    private static final Logger LOGGER =
            Logger.getLogger("com.huanghehua.www.framework.aop.InterceptFactory");
}
