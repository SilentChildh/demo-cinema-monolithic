package com.huanghehua.www.dispatch.handler;

import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.RequestParam;
import com.huanghehua.www.dispatch.exception.HttpQueryParameterException;
import com.huanghehua.www.dispatch.exception.HttpRequestBodyException;
import com.huanghehua.www.dispatch.exception.ParseException;
import com.huanghehua.www.dispatch.exception.ReflectionException;
import com.huanghehua.www.dispatch.model.ControllerMethodInfo;
import com.huanghehua.www.dispatch.util.ConvertUtils;
import com.huanghehua.www.dispatch.util.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 请求处理工具类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class RequestHandler {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.dispatch.com.huanghehua.www.dispatch.util.ParameterHandler");

    /**
     * post请求方法处理
     *
     * @param request              请求
     * @param controllerMethodInfo 控制器方法信息
     * @return {@link CommonResult}<{@link ?}>
     */
    public static CommonResult<?> postMethodHandle(HttpServletRequest request,
                                                   ControllerMethodInfo controllerMethodInfo) {
        // 获取controller方法相关信息
        Object controllerBean = controllerMethodInfo.getControllerBean();
        Method controllerMethod = controllerMethodInfo.getControllerMethod();
        Parameter[] parameters = controllerMethod.getParameters();

        // 参数格式不为1时
        if (parameters.length != 1) {
            LOGGER.log(Level.WARNING, "请求体参数与controller方法参数不匹配");
            throw new HttpRequestBodyException("请求体参数与controller方法参数不匹配");
        }

        Parameter parameter = parameters[0];
        Class<?> parameterClass = parameter.getType();
        try {
            Object bean = JsonUtils.parseJson(request, parameterClass);
            return (CommonResult<?>) controllerMethod.invoke(controllerBean, bean);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "json反序列化失败", e);
            throw new ParseException("json反序列化失败", e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Controller postRequest method invoke fail", e);
            throw new ReflectionException("Controller postRequest method invoke fail", e);
        }
    }

    /**
     * get请求方法处理
     *
     * @param request              请求
     * @param controllerMethodInfo 控制器方法信息
     * @return {@link CommonResult}<{@link ?}>
     */
    public static CommonResult<?> getMethodHandle(HttpServletRequest request,
                                                  ControllerMethodInfo controllerMethodInfo) {
        // 获取查询参数的映射
        Map<String, String> parametersMap = null;
        try {
            parametersMap = RequestHandler.parametersMappingHandle(request);
        } catch (UnsupportedEncodingException e) {
            // TODO
            throw new RuntimeException(e);
        }
        // 接收传入的实参
        List<Object> list = new ArrayList<>(12);

        // 获取方法相关信息
        Method controllerMethod = controllerMethodInfo.getControllerMethod();
        Parameter[] parameters = controllerMethod.getParameters();

        for (Parameter parameter : parameters) {
            boolean annotationPresent = parameter.isAnnotationPresent(RequestParam.class);
            if (annotationPresent) {
                RequestParam annotation = parameter.getAnnotation(RequestParam.class);
                // 获取实参
                String value = annotation.value();
                String actualValue = parametersMap.get(value);
                // 转换为基本类型的包装类，如果是方法参数是字符串则不变
                Object convert = ConvertUtils.convertToWrapperType(actualValue, parameter.getType());
                // 添加实参
                list.add(convert);
            }
        }

        // 转为Object数组
        Object[] parametersArray = list.toArray();

        try {
            // 调用方法
            Object controllerBean = controllerMethodInfo.getControllerBean();
            return (CommonResult<?>) controllerMethod.invoke(controllerBean, parametersArray);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, "Controller getRequest method invoke fail", e);
            throw new ReflectionException("Controller getRequest method invoke fail", e);
        }

    }

    /**
     * 用于将HTTP请求中的Query参数转化为Map映射集合的核心方法
     *
     * @param request 请求
     * @return {@link Map}<{@link String}, {@link String}>
     */
    private static Map<String, String> parametersMappingHandle(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> parametersMap = new HashMap<>(16);
        String queryString = request.getQueryString();
        String[] split = queryString.split("&");

        for (String s : split) {
            String[] map = s.split("=");
            if (map.length != 2) {
                LOGGER.log(Level.INFO, "http请求中的查询字符串参数中缺少相关信息");
                throw new HttpQueryParameterException("http请求中的查询字符串参数中缺少相关信息");
            }
            parametersMap.put(
                    map[0],
                    URLDecoder.decode(map[1], "utf-8")
            );
        }

        LOGGER.log(Level.INFO, "http请求中的查询字符串参数封装到映射集合 successfully");
        return parametersMap;
    }

}
