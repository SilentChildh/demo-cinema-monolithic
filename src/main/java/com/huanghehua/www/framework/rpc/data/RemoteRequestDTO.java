package com.huanghehua.www.framework.rpc.data;

import java.util.Arrays;
import java.util.Objects;

/**
 * 远程请求DTO，用于封装所需远程调用方法的相关信息，
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/05
 */
public class RemoteRequestDTO {
    /**
     * 请求id
     */
    private Integer requestId;
    /**
     * 接口类的class对象
     */
    private Class<?> interfaceClass;
    /**
     * 接口对应的实现类的class对象
     */
    private Class<?> instanceClass;
    /**
     * 需要调用的方法名称
     */
    private String methodName;
    /**
     * 需要调用的方法的参数类型的class对象
     */
    private Class<?>[] parameterTypes;
    /**
     * 参数列表的实参值
     */
    private Object[] parameterValues;

    public RemoteRequestDTO(Integer requestId, Class<?> interfaceClass, Class<?> instanceClass,
                            String methodName, Class<?>[] parameterTypes, Object[] parameterValues) {
        this.requestId = requestId;
        this.interfaceClass = interfaceClass;
        this.instanceClass = instanceClass;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RemoteRequestDTO that = (RemoteRequestDTO) o;
        return Objects.equals(requestId, that.requestId) && Objects.equals(interfaceClass, that.interfaceClass) && Objects.equals(instanceClass, that.instanceClass) && Objects.equals(methodName, that.methodName) && Arrays.equals(parameterTypes, that.parameterTypes) && Arrays.equals(parameterValues, that.parameterValues);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(requestId, interfaceClass, instanceClass, methodName);
        result = 31 * result + Arrays.hashCode(parameterTypes);
        result = 31 * result + Arrays.hashCode(parameterValues);
        return result;
    }

    @Override
    public String toString() {
        return "RemoteRequestDTO{" +
                "requestId=" + requestId +
                ", interfaceClass=" + interfaceClass +
                ", instanceClass=" + instanceClass +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameterValues=" + Arrays.toString(parameterValues) +
                '}';
    }

    public Class<?> getInstanceClass() {
        return instanceClass;
    }

    public void setInstanceClass(Class<?> instanceClass) {
        this.instanceClass = instanceClass;
    }

    public RemoteRequestDTO(Class<?> interfaceClass,
                            String methodName,
                            Class<?>[] parameterTypes, Object[] parameterValues) {
        this.interfaceClass = interfaceClass;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(Object[] parameterValues) {
        this.parameterValues = parameterValues;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
}
