package com.huanghehua.www.common;

import java.util.Objects;

/**
 * 统一的结果，用于返回给前端处理
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/30
 */
public class CommonResult<T>{
    /**
     * 实例数据
     */
    private T data;
    /**
     * HTTP状态码
     */
    private Integer httpCode;
    /**
     * 前后端统一的错误码，其中00000为一切正常
     */
    private String errorCode;
    /**
     * 抛出异常原因
     */
    private String errorMessage;
    /**
     * 给用户的提示消息，包含如何处理该异常，如跳转页面
     */
    private String tipMessage;


    /**
     * 当出现异常时，不需要返回数据时使用
     *
     * @param state      状态
     * @param tipMessage 提示消息
     */
    public CommonResult(State state, String tipMessage) {
        this.data = (T) new Object();
        this.httpCode = state.getHttpCode();
        this.errorCode = state.getErrorCode();
        this.errorMessage = state.getErrorMessage();
        this.tipMessage = tipMessage;
    }

    /**
     * 完整的数据传输
     *
     * @param data       数据
     * @param state      状态
     * @param tipMessage 提示消息
     */
    public CommonResult(T data, State state, String tipMessage) {
        this.data = data;
        this.httpCode = state.getHttpCode();
        this.errorCode = state.getErrorCode();
        this.errorMessage = state.getErrorMessage();
        this.tipMessage = tipMessage;
    }


    /**
     * 操作成功，需要返回数据对象
     *
     * @param data 数据
     * @return {@link CommonResult}<{@link T}>
     */
    public static <T> CommonResult<T> operateSuccess(T data) {
        return new CommonResult<>(data, State.SUCCESS, "操作成功");
    }

    /**
     * 用户端错误，一级宏观错误，自动返回一个空的数据对象。
     *
     * @param tipMessage 提示消息
     * @return {@link CommonResult}<{@link T}>
     */
    public static <T> CommonResult<T> operateFail(String tipMessage) {
        return new CommonResult<>(State.CLIENT_ERROR, tipMessage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommonResult<?> that = (CommonResult<?>) o;
        return Objects.equals(data, that.data) && Objects.equals(httpCode, that.httpCode) && Objects.equals(errorCode, that.errorCode) && Objects.equals(errorMessage, that.errorMessage) && Objects.equals(tipMessage, that.tipMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, httpCode, errorCode, errorMessage, tipMessage);
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "data=" + data +
                ", httpCode=" + httpCode +
                ", errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", tipMessage='" + tipMessage + '\'' +
                '}';
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getTipMessage() {
        return tipMessage;
    }

    public void setTipMessage(String tipMessage) {
        this.tipMessage = tipMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
