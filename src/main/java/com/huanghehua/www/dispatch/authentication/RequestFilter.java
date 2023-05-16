package com.huanghehua.www.dispatch.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.common.State;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 对请求进行解密验证，判断是否被拦截修改。<br/>
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/29
 */
@WebFilter("/*")
public class RequestFilter implements Filter {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    public void init(FilterConfig config) throws ServletException {
        LOGGER.log(Level.INFO, "com.huanghehua.www.dispatch.authentication.RequestFilter is created");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin","*");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods","POST");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers","content-type");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        LOGGER.log(Level.INFO, "RequestFilter is invoking.");

        try {
            // 如果是登录URL的请求，则无需验证
            final String URI = "/authentication";
            LOGGER.log(Level.WARNING, httpRequest.getRequestURI());
            if (!httpRequest.getRequestURI().contains(URI)) {
                // 验证jwt
                verify(httpRequest);
                LOGGER.log(Level.INFO, "jwt 验证通过.");
            }

        } catch (JWTVerificationException e) {
            this.jwtVerificationExceptionInterceptor(e);
            return;
        }

        String time = httpRequest.getParameter("time");
        String sign = httpRequest.getParameter("sign");

        /*if (time == null || sign == null) {
            LOGGER.log(Level.SEVERE, "Verify fail");
            return;
        }*/
        boolean status = true;//sign.equals(DigestUtils.md5Hex(time));

        if (status) {
            LOGGER.log(Level.INFO, "Verify result : {0}" ,status);
            chain.doFilter(httpRequest, response);
        }
        else {
            LOGGER.log(Level.SEVERE, "Verify fail");
        }
    }

    private static final String SECRET_KEY = "?????????????";


    /**
     * 验证
     *
     * @param request 请求
     */
    private void verify(HttpServletRequest request) {
        final String jwt = "jwt";
        String queryString = request.getQueryString();
        if (queryString == null || !queryString.contains(jwt)) {
            Logger.getAnonymousLogger().log(Level.WARNING, "JWT验证失败");
            throw new JWTVerificationException("JWT验证失败");
        }


        String[] splits = queryString.split("&");
        // 拆分为Key与value
        for (String split : splits) {
            String[] kv = split.split("=");
            // 获取token
            if (jwt.equals(kv[0])) {
                try {
                    // 当验证失败时，抛出异常
                    verifyJwt(URLDecoder.decode(kv[1], "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.log(Level.SEVERE, "无法对URL进行解码", e);
                    throw new RuntimeException("无法对URL进行解码", e);
                }
            }
        }
    }

    /**
     * 验证jwt
     *
     * @param token 令牌
     */
    private void verifyJwt(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        } catch (JWTVerificationException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "JWT验证失败", e);
            throw e;
        }
    }

    private CommonResult<?> jwtVerificationExceptionInterceptor(Throwable e) {
        LOGGER.log(Level.INFO, "JWT验证失败", e);
        return new CommonResult<>(State.CLIENT_REQUEST_SERVICE_ERROR, "JWT不存在");
    }

}
