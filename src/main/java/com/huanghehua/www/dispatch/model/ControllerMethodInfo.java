package com.huanghehua.www.dispatch.model;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 控制器方法信息，
 * 一是用于连接controller方法与请求路径的映射<br/>
 * 二是用于连接DispatchServlet与ApplicationExceptionHandler，两者是通过RequestHandler异步回调间接关联的<br/>
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/02
 */
public class ControllerMethodInfo {
    /**
     * controller类的bean实例
     */
    private Object controllerBean;
    /**
     * controller类的方法
     */
    private Method controllerMethod;
    /**
     * http请求方法类型
     */
    private String requestMethod;

    public ControllerMethodInfo(Object controllerBean, Method controllerMethod, String requestMethod) {
        this.controllerBean = controllerBean;
        this.controllerMethod = controllerMethod;
        this.requestMethod = requestMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ControllerMethodInfo that = (ControllerMethodInfo) o;
        return Objects.equals(controllerBean, that.controllerBean) && Objects.equals(controllerMethod, that.controllerMethod) && Objects.equals(requestMethod, that.requestMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controllerBean, controllerMethod, requestMethod);
    }

    @Override
    public String toString() {
        return "ControllerMethodInfo{" +
                "controllerClass=" + controllerBean +
                ", controllerMethod=" + controllerMethod +
                ", requestMethod='" + requestMethod + '\'' +
                '}';
    }

    public Object getControllerBean() {
        return controllerBean;
    }

    public void setControllerBean(Object controllerBean) {
        this.controllerBean = controllerBean;
    }

    public void setControllerMethod(Method controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
