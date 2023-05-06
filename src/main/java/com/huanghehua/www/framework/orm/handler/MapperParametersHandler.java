package com.huanghehua.www.framework.orm.handler;


import com.huanghehua.www.orm.annotation.Param;
import com.huanghehua.www.orm.exception.NotFoundMappingMethodException;
import com.huanghehua.www.orm.model.MethodDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 用于在执行CRUD操作前，将外界传入的多参数转换为单参数，即Map或者Object实例.<br/>
 * <p/>
 * 特别地，当无参的情况下，返回的是一个新建的Object对象，而不是返回null。<br/>
 * 在单参数情况下，如果本身就是null，那么将抛出异常。<br/>
 * 在多参数的情况下，如果传入的还是有null值，那么不保证能够排除掉这些null值。<br/>
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/12
 */

public class MapperParametersHandler {
    /**
     * 注解{@link Param}的类对象，将用于判断是否存在该注解。
     */
    private static final Class<? extends Annotation> PARAM_ANNOTATION = Param.class;

    /**
     * 将多参数转换为单参数。<br/>
     * <p/>
     * 特别地，当无参的情况下，返回的是一个新建的Object对象，而不是返回null。<br/>
     * 在单参数情况下，如果本身就是null，那么将抛出异常。<br/>
     * 在多参数的情况下，如果传入的还是有null值，那么不保证能够排除掉这些null值。<br/>
     *
     * @return {@link Object} 返回一个单参数
     */
    public static Object handle(MethodDefinition methodDefinition) {
        // 得到方法信息
        Class<?> interfaceClass = methodDefinition.getInterfaceClass();
        Method method1 = methodDefinition.getMethod();
        Object[] args = methodDefinition.getArgs();

        // 得到对应的方法
        Method method = null;
        // 获取指定类的所有方法
        Method[] declaredMethods = interfaceClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            // 如果找到该方法则返回
            if (method1.getName().equals(declaredMethod.getName())) {
                method =  declaredMethod;
            }
        }
        // 否则直接抛出异常
        if (method == null) {
            LOGGER.log(Level.SEVERE, "未找到指定接口中的CRUD方法");
            throw new NotFoundMappingMethodException("未找到指定接口中的CRUD方法");
        }

        // 用于存储原Map集合元素或者被注解修饰的参数。
        final Map<String, Object> parametersMap = new HashMap<>(1024);
        // 获取方法形参的数据
        Parameter[] parameters = method.getParameters();


        // 特判无参
        boolean noneParameter = parameters.length == 0;
        if (noneParameter) {
            // 避免返回null，故新建一个Object对象
            return new Object();
        }
        // 只有一个实参且无注解修饰时的情况，当parameters[0]为null时会抛出异常
        boolean singleParameter = parameters.length == 1 && (!parameters[0].isAnnotationPresent(PARAM_ANNOTATION));
        if (singleParameter) {
            return args[0];
        }
        // 否则进行是否带有的注解判断
        else {
            // 如果第一个实参是Map，则将其元素放入代理类中的Map集合中
            if (args[0] instanceof Map) {
                parametersMap.putAll((Map<String, Object>) args[0]);
            }
            // 如果实参不是Map，则判断是否有注解
            else {
                for (int i = 0; i < parameters.length; i++) {
                    // 判断是否被Param注解修饰
                    if (parameters[i].isAnnotationPresent(PARAM_ANNOTATION)) {
                        // 如果是，就获取该注解中的值作为K，传入的实参作为V，然后将其放入Map中
                        Param param = (Param) parameters[i].getAnnotation(PARAM_ANNOTATION);
                        parametersMap.put(param.value(), args[i]);
                    }
                }
            }

            return parametersMap;

        }
    }
    private static final Logger LOGGER =
            Logger.getLogger("com.huanghehua.www.framework.orm.handler.ParametersHandler");
}
