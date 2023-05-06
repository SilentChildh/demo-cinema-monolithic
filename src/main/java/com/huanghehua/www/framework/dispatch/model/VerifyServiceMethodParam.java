package com.huanghehua.www.framework.dispatch.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * 用于封装controller需要调用的service方法的相关信息。<br/>
 * <p/>
 * 主要用于封装方法信息，完成参数验证。<br/>
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/01
 */
public class VerifyServiceMethodParam {
    /**
     * service类的class对象
     */
    private Class<?> serviceClass;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法的参数列表
     */
    private Class<?>[] args;
    /**
     * 需要传给方法的实参列表
     */
    private Object[] value;

    public VerifyServiceMethodParam(Class<?> serviceClass, String methodName, Class<?>[] args, Object[] value) {
        this.serviceClass = serviceClass;
        this.methodName = methodName;
        this.args = args;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MethodInfo{" +
                "serviceClass=" + serviceClass +
                ", methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                ", value=" + Arrays.toString(value) +
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
        VerifyServiceMethodParam that = (VerifyServiceMethodParam) o;
        return Objects.equals(serviceClass, that.serviceClass) && Objects.equals(methodName, that.methodName) && Arrays.equals(args, that.args) && Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(serviceClass, methodName);
        result = 31 * result + Arrays.hashCode(args);
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getArgs() {
        return args;
    }

    public void setArgs(Class<?>[] args) {
        this.args = args;
    }

    public Object[] getValue() {
        return value;
    }

    public void setValue(Object[] value) {
        this.value = value;
    }
}
