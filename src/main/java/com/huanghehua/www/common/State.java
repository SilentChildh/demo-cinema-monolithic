package com.huanghehua.www.common;

/**
 * 状态，包含HTTP状态码和对应的错误码、错误信息
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/30
 */
public enum State {
    /**
     * 成功结果
     */
    SUCCESS(200, "00000", "一切 ok"),
    /**
     * 服务器错误，一级宏观错误
     */
    SERVER_ERROR(500, "B0001", "服务器错误"),
    /**
     * 用户端错误，一级宏观错误
     */
    CLIENT_ERROR(400, "A0001", "用户端错误"),
    /**
     * 用户注册错误，二级宏观错误。当前请求需要用户验证。
     */
    CLIENT_REGISTER(401, "A0100", "用户注册错误"),
    /**
     * 用户登录异常，二级宏观错误。当前请求需要用户验证。
     */
    CLIENT_LOGIN(401, "A0200", "用户登录异常"),
    /**
     * 访问权限异常，二级宏观错误。请求行中指定的请求方法不能被用于请求相应的资源。
     */
    CLIENT_NO_PERMISSIONS(405, "A0300", "访问权限异常"),
    /**
     * 用户请求参数错误，二级宏观错误。
     * 例如用户输入了一个不合法参数类型的数据
     */
    CLIENT_BAD_PARAMETERS(400, "A0400", "用户请求参数错误"),
    /**
     * 用户请求服务异常, 二级宏观错误。即服务器已经理解请求，但是拒绝执行它。
     * 例如用户重复请求
     */
    CLIENT_REQUEST_SERVICE_ERROR(403, "A0500", "用户请求服务异常"),
    /**
     * 用户资源异常, 二级宏观错误。即请求所希望得到的资源未被在服务器上发现。
     * 例如用户访问了不存在的站点
     */
    CLIENT_RESOURCE_ERROR(404, "A0600", "用户资源异常"),
    /**
     * 服务器超时, 二级宏观错误。
     */
    SERVER_TIMEOUT(503, "B0100", "系统执行超时"),
    /**
     * 服务器资源错误, 二级宏观错误。
     */
    SERVER_RESOURCE_ERROR(501, "B0300", "系统资源异常");



    /**
     * HTTP状态码
     */
    private Integer httpCode;
    /**
     * 前后端统一的错误码，其中00000为一切正常
     */
    private String errorCode;
    /**
     * 返回信息
     */
    private String errorMessage;

    State(Integer httpCode, String errorCode, String errorMessage) {
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    @Override
    public String toString() {
        return "State{" +
                "httpCode=" + httpCode +
                ", errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }
}
