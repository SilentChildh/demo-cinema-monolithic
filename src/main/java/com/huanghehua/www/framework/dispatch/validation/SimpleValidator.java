package com.huanghehua.www.framework.dispatch.validation;

import com.huanghehua.www.dispatch.exception.ReflectionException;
import com.huanghehua.www.dispatch.validation.annotation.*;
import com.huanghehua.www.dispatch.validation.exception.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 验证器，用于为属性或者方法参数进行验证
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class SimpleValidator {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.com.huanghehua.www.dispatch.validation.Validator");

    /**
     * JSR303注解与验证方法的映射集合
     */
    private static final Map<Class<Annotation>, Method> VERIFY_METHOD_MAP = new HashMap<>(16);

    /*
     * 注册注解和验证方法到映射集合中
     */
    static {
        // 获取本类的所有方法
        Method[] declaredMethods = SimpleValidator.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            // 获取注解，一般来说，验证方法上只会出现一个注解
            Annotation[] methodAnnotations = declaredMethod.getDeclaredAnnotations();
            if (methodAnnotations.length == 0) {
                continue;
            }
            VerifyType methodAnnotation = (VerifyType) methodAnnotations[0];
            SimpleValidator.VERIFY_METHOD_MAP.put((Class<Annotation>) methodAnnotation.value(), declaredMethod);
        }
    }

    /**
     * 传入一个Field实例或者Parameter实例，验证参数是否符合限制
     *
     * @param object 属性或参数对象，即Field实例或者Parameter实例
     * @param value  需要注入给属性或者方法参数的值
     */
    public static void verify(Object object, Object value) {
        Class<?> objectClass = object.getClass();
        AnnotatedElement annotatedElement;

        if (objectClass == Parameter.class || objectClass == Field.class) {
            annotatedElement = (AnnotatedElement) object;
            SimpleValidator.VERIFY_METHOD_MAP.forEach((annotationClass, verifyMethod) -> {
                boolean annotationPresent = annotatedElement.isAnnotationPresent(annotationClass);

                if (annotationPresent) {
                    try {
                        if (annotationClass.equals(Size.class) ||
                                annotationClass.equals(RegexPattern.class) ||
                                annotationClass.equals(Range.class)) {
                            Annotation annotation = annotatedElement.getAnnotation(annotationClass);
                            verifyMethod.invoke(null, annotation, value);
                        }
                        else {
                            verifyMethod.invoke(null, value);
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        LOGGER.log(Level.SEVERE, "反射调用验证方法fail", e);
                        throw new ReflectionException("反射调用验证方法fail", e);
                    }
                }
            });
        }
    }

    /**
     * 验证实例是否为null
     *
     * @param value 价值
     */
    @VerifyType(NotNull.class)
    private static void verifyNotNull(Object value) {
        if (value == null) {
            LOGGER.log(Level.WARNING, "实例值不能为null");
            throw new NullPointerException("实例值不能为null");
        }
    }

    /**
     * 验证字符串是否为空串或null
     *
     * @param value 价值
     */
    @VerifyType(NotBlank.class)
    private static void verifyNotBlank(Object value) {
        if (value == null || "".equals(value)) {
            LOGGER.log(Level.WARNING, "实例值不能为null或者空串");
            throw new BlankException("实例值不能为null或者空串");
        }
    }

    /**
     * 验证字符串、数组、集合不为null，且长度不为0
     *
     * @param value 价值
     */
    @VerifyType(NotEmpty.class)
    private static void verifyNotEmpty(Object value) {

        if ("".equals(value)) {
            LOGGER.log(Level.WARNING, "实例值不能为空串");
            throw new EmptyException("实例值不能为空串");
        }


        Class<?> valueClass = value.getClass();
        /*
         * 判断数组
         */
        if (valueClass.isArray()) {
            for (Object object : (Object[]) value) {
                if (object == null) {
                    LOGGER.log(Level.WARNING, "实例数组中元素不能为null");
                    throw new EmptyException("实例数组中元素不能为null");
                }
            }
        }

        /*
         * 接下来开始判断集合
         */
        Object instance;
        try {
            instance = valueClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "创建实例值类型的新实例失败", e);
            throw new RuntimeException("创建实例值类型的新实例失败", e);
        }

        // 记录是否是集合
        boolean isEmpty = false;
        // 判断是否是集合
        if (instance instanceof Collection) {
            Collection<?> objects = (Collection<?>) instance;
            isEmpty = objects.isEmpty();
        } else if (instance instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) instance;
            isEmpty = map.isEmpty();
        }
        if (isEmpty) {
            LOGGER.log(Level.WARNING, "集合元素个数不能为0");
            throw new EmptyException("集合元素个数不能为0");
        }


    }

    /**
     * 验证是否字符串、数组、集合长度是否在范围之内
     *
     * @param value 价值
     */
    @VerifyType(Size.class)
    private static void verifySize(Annotation annotation, Object value) {
        Size size = (Size) annotation;
        int min = size.min();
        int max = size.max();

        int length = 0;
        Class<?> valueClass = value.getClass();
        if (value instanceof String) {
            String s = (String) value;
            length = s.length();
        }
        else if (valueClass.isArray()) {
            Object[] objects = (Object[]) value;
            length = objects.length;
        }
        else if (value instanceof Collection) {
            Collection<?> objects = (Collection<?>) value;
            length = objects.size();
        } else if (value instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) value;
            length = map.size();
        }


        if (min < 0) {
            LOGGER.log(Level.WARNING, "最小长度不能为负数");
            throw new NotNegativeNumberException("最小长度不能为负数");
        }

        if (!(min <= length && length <= max)) {
            LOGGER.log(Level.WARNING, "实例的长度范围不符合要求");
            throw new RangeException("实例的长度范围不符合要求");
        }
    }

    /**
     * 验证字符串值是否符合正则表达式
     *
     * @param value 价值
     */
    @VerifyType(RegexPattern.class)
    private static void verifyRegexPattern(Annotation annotation, Object value) {
        RegexPattern regexPattern = (RegexPattern) annotation;
        String regex = regexPattern.value();
        String val = (String) value;
        boolean match = val.matches(regex);

        if (!match) {
            LOGGER.log(Level.WARNING, "字符串模式不匹配");
            throw new RegexException("字符串模式不匹配");
        }
    }

    /**
     * 验证数值大小是否在范围之内
     *
     * @param value 价值
     */
    @VerifyType(Range.class)
    private static void verifyRange(Annotation annotation, Object value) {
        Range range = (Range) annotation;
        // 转换为字符串再解析为int，防止数值类型不兼容
        int val = Integer.parseInt(value.toString());

        int min = range.min();
        int max = range.max();

        if (!(min <= val && val <= max)) {
            LOGGER.log(Level.WARNING, "实例的数值范围不符合要求");
            throw new RangeException("实例的数值范围不符合要求");
        }
    }
}
