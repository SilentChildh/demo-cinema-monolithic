package com.huanghehua.www.dispatch;

import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.common.State;
import com.huanghehua.www.dispatch.exception.ReflectionException;
import com.huanghehua.www.dispatch.handler.ApplicationExceptionHandler;
import com.huanghehua.www.dispatch.handler.RequestHandler;
import com.huanghehua.www.dispatch.manager.RegisterManager;
import com.huanghehua.www.dispatch.model.ControllerMethodInfo;
import com.huanghehua.www.dispatch.util.JsonUtils;
import com.huanghehua.www.ioc.ApplicationBeanContext;
import com.huanghehua.www.orm.util.SimpleSqlSessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 请求转发器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/29
 */
@WebServlet(name = "com.huanghehua.www.dispatch.DispatchServlet", value = "/api/*")
public class DispatchServlet extends HttpServlet {
    private static final long serialVersionUID = -5631774534069631333L;
    /**
     * 请求路径与controller方法的映射，K为请求路径（即/项目名+K），K开头是带“/”的，V为{@link ControllerMethodInfo}
     */
    private static Map<String, ControllerMethodInfo> requestMap;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.log(Level.INFO, "Method doGet() was invoked by {0}", Thread.currentThread());
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.log(Level.INFO, "Method doPost() was invoked by {0}", Thread.currentThread());
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","post");

        // 接收响应结果
        CommonResult<?> dispatchResult = dispatch(request);
        // 设置响应码
        // TODO：设置错误码后，axios会catch处理error
        /*Integer httpCode = dispatchResult.getHttpCode();
        response.setStatus(httpCode);*/
        // 响应请求
        response.getWriter().write(JsonUtils.toJsonString(dispatchResult));

    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Method doOptions() was invoked by {0}", Thread.currentThread());
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","POST");
        response.setHeader("Access-Control-Allow-Headers","content-type");
    }

    /**
     * 请求转发的核心方法。负责将符合规范的请求路径通过全局异常处理器完成对controller的静态代理，
     * 再进一步通过全局异常处理器其去调用controller相关的method。<br/>
     * <p/>
     * 当不存在对应的请求路径时，提示用户站点错误，否则返回相应的数据（可能是正确的结果，也可能是其他错误原因）。<br/>
     *
     * @param request 请求
     * @return {@link CommonResult}<{@link ?}>
     */
    private CommonResult<?> dispatch(HttpServletRequest request) {
        // 静态代理，即全局异常处理器
        ApplicationExceptionHandler applicationExceptionHandler = new ApplicationExceptionHandler();
        // 获取请求的URI
        String requestUri = request.getRequestURI();
        // 获取项目名
        String contextPath = request.getContextPath();
        // 获取该请求中servlet路径
        String servletPath = request.getServletPath();
        // 获取前端发来的请求方法
        String requestMethod = request.getMethod();

        // 遍历映射容器
        for (Map.Entry<String, ControllerMethodInfo> entry : requestMap.entrySet()) {
            // 获取方法路径
            String requestPath = entry.getKey();
            ControllerMethodInfo controllerMethodInfo = entry.getValue();

            // 请求路径不匹配则跳过
            boolean matches = requestUri.matches(contextPath + servletPath + requestPath);
            if (!matches) {
                continue;
            }

            // 利用全局异常处理器进行代理，或者因请求方法不符合规范直接错误数据
            try {
                String annotationRequestMethod = controllerMethodInfo.getRequestMethod();
                if ("get".equalsIgnoreCase(annotationRequestMethod)) {
                    return applicationExceptionHandler.handler(() ->
                            RequestHandler.getMethodHandle(request, controllerMethodInfo));
                } else if ("post".equalsIgnoreCase(annotationRequestMethod)) {
                    return applicationExceptionHandler.handler(() ->
                            RequestHandler.postMethodHandle(request, controllerMethodInfo));
                }
                // 请求方法不符合规范，直接返回一个包含错误信息的DTO
                else if (!annotationRequestMethod.equalsIgnoreCase(requestMethod)) {
                    return new CommonResult<>(State.SERVER_RESOURCE_ERROR, "服务器资源类型不正确");
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                LOGGER.log(Level.SEVERE, "Application exceptionHandler invoke fail", e);
                throw new ReflectionException("Application exceptionHandler invoke fail", e);
            }
        }

        // 不存在请求站点，直接返回错误信息
        return new CommonResult<>(State.CLIENT_RESOURCE_ERROR, "您尝试访问的站点不存在，请输入正确的地址");
    }


    /**
     * 注册bean实例，以及注册请求与controller的对应关系.<br/>
     * <p/>
     * 得先注册bean实例，再注册请求路径映射，否则controllerBean实例无法获取
     */
    @Override
    public void init() {
        final String packageName = "com.huanghehua.www";
        // 注册bean实例
        ApplicationBeanContext applicationBeanContext = new ApplicationBeanContext();
        applicationBeanContext.registerMetaBean(packageName);

        // 注册请求与controller的对应关系
        try {
            requestMap = RegisterManager.registerController(packageName);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "ClassNotFound in registerController()", e);
            throw new ReflectionException("ClassNotFound in registerController()", e);
        }

    }


    /**
     * 关闭连接池资源
     */
    @Override
    public void destroy() {
        SimpleSqlSessionUtil.destroy();
    }

    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.dispatch.DispatchServlet");
}
