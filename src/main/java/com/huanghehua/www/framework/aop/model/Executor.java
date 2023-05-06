package com.huanghehua.www.framework.aop.model;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 执行器，包含被调用的业务方法的相关信息。<br/>
 * <p/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/18
 */
public class Executor {
    /**
     * 业务类bean实例
     */
    private Object bean;
    /**
     * 业务方法
     */
    private Method method;
    /**
     * 传入业务方法的实参数组
     */
    private Object[] args;

    public Executor() {
    }

    public Executor(Object bean, Method method, Object[] args) {
        this.bean = bean;
        this.method = method;
        this.args = args;
    }

    @Override
    public String toString() {
        return "Executor{" +
                "bean=" + bean +
                ", method=" + method +
                ", args=" + Arrays.toString(args) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Executor executor = (Executor) o;
        return Objects.equals(bean, executor.bean) && Objects.equals(method, executor.method) && Arrays.equals(args, executor.args);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(bean, method);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
