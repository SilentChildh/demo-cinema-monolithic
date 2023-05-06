package com.huanghehua.www.dispatch.manager;

import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.dispatch.model.ControllerMethodInfo;
import com.huanghehua.www.dispatch.util.ScannerUtils;
import com.huanghehua.www.ioc.ApplicationBeanContext;
import com.huanghehua.www.ioc.BeanFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 注册管理器，用于注册请求路径与controller方法的映射关系
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/14
 */
public class RegisterManager {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.dispatch.manager.RegisterManager");
    private static final Class<Request> REQUEST_PATH_CLASS = Request.class;
    /**
     * 注册控制器，将对应的请求路径与controller实例、controller方法注册到Map集合中。<br/>
     * <p/>
     * 获取controller实例一定要从IoC容器中获取，否则内部的属性无法自动注入.<br/>
     *
     * @param packageName 包名
     * @return {@link Map}<{@link String}, {@link ControllerMethodInfo}> 请求路径与controller方法的映射
     *                          K为请求路径（即/项目名+K），K开头是带“/”的，V为{@link ControllerMethodInfo}<br/>
     *
     * @throws ClassNotFoundException 类没有发现异常
     */
    public static Map<String, ControllerMethodInfo> registerController(String packageName) throws ClassNotFoundException {
        // 映射容器
        Map<String, ControllerMethodInfo> requestMap = new HashMap<>(64);
        // bean工厂，用于获取bean实例
        BeanFactory beanFactory = new ApplicationBeanContext();

        List<File> fileFromPackage = ScannerUtils.getFileFromPackage(packageName, ".class");
        // TODO: 一切注册的容器可能都存在相同的问题：当启动服务器之后，更新资源时，不会再将新资源纳入容器中，故无法访问到新资源
        for (File file : fileFromPackage) {

            // 获取全限定类名
            String path = file.getPath();
            int begin = path.indexOf(packageName.replace(".", "\\"));
            String className = path.substring(begin, path.length() - 6).replace("\\", ".");

            // 获取controller类对象
            Class<?> controllerClass = Class.forName(className);
            // 接收controller请求路径
            String controllerPath = "";

            boolean annotationPresent = controllerClass.isAnnotationPresent(REQUEST_PATH_CLASS);
            if (annotationPresent) {
                // 获取controller请求路径
                Request annotation = controllerClass.getDeclaredAnnotation(REQUEST_PATH_CLASS);
                controllerPath = annotation.value();
            }

            // 获取controller具体方法的请求路径
            if (annotationPresent) {
                Method[] declaredMethods = controllerClass.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {

                    boolean present = declaredMethod.isAnnotationPresent(REQUEST_PATH_CLASS);
                    if (present) {
                        Request declaredAnnotation = declaredMethod.getDeclaredAnnotation(REQUEST_PATH_CLASS);
                        // 获取具体方法的请求路径
                        String methodPath = declaredAnnotation.value();
                        String requestPath = controllerPath + methodPath;
                        // 获取http请求方法
                        String requestMethod = declaredAnnotation.method();

                        // 获取controller实例(一定要从IoC容器中获取，否则内部的属性无法自动注入)
                        Object controllerBean = beanFactory.getBean(controllerClass);
                        requestMap.put(requestPath,
                                new ControllerMethodInfo(controllerBean, declaredMethod, requestMethod));
                    }
                }
            }

        }
        LOGGER.log(Level.INFO, "注册controller方法 successfully");
        return requestMap;
    }
}
