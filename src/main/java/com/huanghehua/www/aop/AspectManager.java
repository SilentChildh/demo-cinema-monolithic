package com.huanghehua.www.aop;

import com.huanghehua.www.aop.annotation.After;
import com.huanghehua.www.aop.annotation.Around;
import com.huanghehua.www.aop.annotation.Aspect;
import com.huanghehua.www.aop.annotation.Before;
import com.huanghehua.www.aop.exception.ReflectionException;
import com.huanghehua.www.aop.util.ScannerUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 切面类管理器，用于将切面类的横切逻辑方法放入容器中统一管理。<br/>
 * <p/>
 * 被拦截的业务方法都将访问本类维护的容器，以便获取所需的横切逻辑代码。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/18
 */
public class AspectManager {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.aop.AspectManager");

    /**
     * 横切逻辑代码中被{@link Before}修饰的的Map映射
     * K为能够匹配业务方法的正则表达式，V为全限定横切方法名
     */
    private static final Map<String, String> BEFORE_ADVICES = new HashMap<>();
    /**
     * 横切逻辑代码中被{@link Around}修饰的的Map映射
     * K为能够匹配业务方法的正则表达式，V为全限定横切方法名
     */
    private static final Map<String, String> AROUND_ADVICES = new HashMap<>();
    /**
     * 横切逻辑代码中被{@link After}修饰的的Map映射
     * K为能够匹配业务方法的正则表达式，V为全限定横切方法名
     */
    private static final Map<String, String> AFTER_ADVICES = new HashMap<>();


    public static Map<String, String> getBeforeAdvices() {
        return AspectManager.BEFORE_ADVICES;
    }
    public static Map<String, String> getAroundAdvices() {
        return AspectManager.AROUND_ADVICES;
    }
    public static Map<String, String> getAfterAdvices() {
        return AspectManager.AFTER_ADVICES;
    }
    /**
     * 通过全限定包名，将包下被{@link Aspect}修饰的类注册到{@code metaBeanMap}容器中。<br/>
     * <p/>
     *
     * @param packageName 全限定包名
     */
    public static void registerAspect(String packageName) {
        // 获取class文件
        List<File> classFiles = AspectManager.getBeanClassFileFromPackage(packageName);

        // 得到元bean，并加入到metaBeanMap容器中
        for (File file : classFiles) {
            // 接收可能为bean实例的class对象
            Class<?> perhapsBean = loadPerhapsBeanClass(file, packageName);

            // 判断是否是Aspect切面,是则进行注册管理
            boolean aspectAnnotationPresent = perhapsBean.isAnnotationPresent(ASPECT_ANNOTATION_CLASS);
            // 是则进行注册管理
            if (aspectAnnotationPresent) {
                AspectManager.registerAspectMethods(perhapsBean);
            }
        }
    }

    /**
     * 注册切面类
     *
     * @param aspect 方面
     */
    private static void registerAspectMethods(Class<?> aspect) {
        // 全限定类名
        String aspectName = aspect.getName();
        // 获取切面中的所有方法
        Method[] aspectDeclaredMethods = aspect.getDeclaredMethods();

        Arrays.stream(aspectDeclaredMethods).forEach(method -> {
            // 获取全限定方法名
            String methodName = method.getName();
            String baseName = aspectName + '.' + methodName;

            // 获取该方法上的所有注解
            Annotation[] annotations = method.getAnnotations();
            // 遍历注解，并匹配三种注解
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(BEFORE_ANNOTATION_CLASS)) {
                    Before beforeAnnotation = (Before) annotation;
                    String regex = beforeAnnotation.value();
                    BEFORE_ADVICES.put(regex, baseName);
                }

                if (annotation.annotationType().equals(AROUND_ANNOTATION_CLASS)) {
                    Around aroundAnnotation = (Around) annotation;
                    String regex = aroundAnnotation.value();
                    AROUND_ADVICES.put(regex, baseName);
                }

                if (annotation.annotationType().equals(AFTER_ANNOTATION_CLASS)) {
                    After afterAnnotation = (After) annotation;
                    String regex = afterAnnotation.value();
                    AFTER_ADVICES.put(regex, baseName);
                }

            }
        });
    }


    /**
     * 扫描加载的可能是bean实例的类
     *
     * @param file        文件
     * @param packageName 包名
     * @return {@link Class}<{@link ?}>
     */
    private static Class<?> loadPerhapsBeanClass(File file, String packageName) {
        // 拼接全限定类名
        String path = file.getPath();
        int begin = path.indexOf(packageName.replace('.', '\\'));
        String className = path.substring(begin, path.length() - 6).replace('\\', '.');

        try {
            // 加载类，获取class对象
            return CLASS_LOADER.loadClass(className);

        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "LoadClassFail", e);
            throw new ReflectionException("LoadClassFail", e);
        }
    }

    /**
     * 从包获取bean类文件
     *
     * @param packageName 包名
     * @return {@link List}<{@link File}>
     */
    private static List<File> getBeanClassFileFromPackage(String packageName) {

        final String suffix = ".class";
        // 获取字节码文件，并返回结果
        return ScannerUtils.getFileFromPackage(packageName, suffix);
    }




    private static final Class<Before> BEFORE_ANNOTATION_CLASS = Before.class;
    private static final Class<Around> AROUND_ANNOTATION_CLASS = Around.class;
    private static final Class<After> AFTER_ANNOTATION_CLASS = After.class;
    private static final Class<Aspect> ASPECT_ANNOTATION_CLASS = Aspect.class;
    private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();
}
