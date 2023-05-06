package com.huanghehua.www.rpc.data;

import java.util.Objects;

/**
 * 远程响应dto，用于封装传输远程调用结果
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/05
 */
public class RemoteResponseDTO {
    /**
     * 请求id
     */
    private Integer requestId;
    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 异常值
     */
    private Exception exceptionValue;


    public RemoteResponseDTO(Integer requestId, Object returnValue, Exception exceptionValue) {
        this.requestId = requestId;
        this.returnValue = returnValue;
        this.exceptionValue = exceptionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RemoteResponseDTO that = (RemoteResponseDTO) o;
        return Objects.equals(returnValue, that.returnValue) && Objects.equals(exceptionValue, that.exceptionValue) && Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(returnValue, exceptionValue, requestId);
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "returnValue=" + returnValue +
                ", exceptionValue=" + exceptionValue +
                ", requestId=" + requestId +
                '}';
    }

    public RemoteResponseDTO() {
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getExceptionValue() {
        return exceptionValue;
    }

    public void setExceptionValue(Exception exceptionValue) {
        this.exceptionValue = exceptionValue;
    }
}
