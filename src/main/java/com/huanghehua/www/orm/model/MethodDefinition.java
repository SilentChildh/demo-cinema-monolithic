package com.huanghehua.www.orm.model;

import com.huanghehua.www.orm.handler.MapperParametersHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 包含一个方法的相关信息，用于多参数处理{@link MapperParametersHandler}。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/18
 */
public class MethodDefinition {
    /**
     * 对应DAO接口的class对象
     */
    private Class<?> interfaceClass;
    /**
     * 指定方法
     */
    private Method method;
    /**
     * 实参参数数组
     */
    private Object[] args;

    public MethodDefinition(Class<?> interfaceClass, Method method, Object[] args) {
        this.interfaceClass = interfaceClass;
        this.method = method;
        this.args = args;
    }

    @Override
    public String toString() {
        return "MethodDefinition{" +
                "interfaceClass=" + interfaceClass +
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
        MethodDefinition that = (MethodDefinition) o;
        return Objects.equals(interfaceClass, that.interfaceClass) && Objects.equals(method, that.method) && Arrays.equals(args, that.args);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(interfaceClass, method);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }


    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
