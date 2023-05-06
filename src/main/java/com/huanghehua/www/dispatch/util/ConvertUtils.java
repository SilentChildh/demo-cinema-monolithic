package com.huanghehua.www.dispatch.util;

import com.huanghehua.www.ioc.exception.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 转化字段类型的工具类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/02
 */
public class ConvertUtils {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.ConvertUtils");

    /**
     * 用于将字符串转换为bean实例中字段对应类型的值。<br/>
     * <p/>
     * 该方法可以检测到八大基本类型以及对应的包装类，并将字符串统一转换为他们的包装类。<br/>
     * 该方法将对字符串的转换进行特判兼容，虽然实际转换后和原字符串的值并没有什么区别。<br/>
     *
     * @param value     需要转换的字符串值
     * @param fieldType 需要注入的字段类型
     * @return {@link Object}
     */
    public static Object convertToWrapperType(String value, Class<?> fieldType) {
        final String valueOf = "valueOf";

        // 特判是否是字符串
        if (fieldType == String.class) {
            return value;
        }

        // 接收转换后的值
        Object actual = new Object();

        // 判断是不是八大基本数据类型
        boolean primitive = fieldType.isPrimitive();
        if (primitive) {
            String primitiveName = fieldType.getSimpleName();
            switch (primitiveName) {
                case "boolean":
                    actual = Boolean.parseBoolean(value);
                    break;
                case "byte":
                    actual = Byte.parseByte(value);
                    break;
                case "short":
                    actual = Short.parseShort(value);
                    break;
                case "int":
                    actual = Integer.parseInt(value);
                    break;
                case "long":
                    actual = Long.parseLong(value);
                    break;
                case "float":
                    actual = Float.parseFloat(value);
                    break;
                case "double":
                    actual = Double.parseDouble(value);
                    break;
                case "char":
                    actual = value.charAt(0);
                    break;
                default:
                    break;
            }
            // 因为引用类型是Object，故转换的时候会自动装箱，即返回的是一个对应的包装类
            return actual;
        }


        try {
            // 获取valueOf方法
            Method valueOfMethod = fieldType.getDeclaredMethod(valueOf, String.class);
            // 开始转换， 并返回
            return valueOfMethod.invoke(actual, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, "通过valueOf方法将字符串转换为包装类型失败", e);
            throw new ParseException("通过valueOf方法将字符串转换为包装类型失败", e);
        }



    }
}
