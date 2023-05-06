package com.huanghehua.www.dispatch.authentication;

import jakarta.servlet.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 对请求进行解密验证，判断是否被拦截修改。<br/>
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/29
 */
/*@WebFilter("/*")*/
public class RequestFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        LOGGER.log(Level.INFO, "com.huanghehua.www.dispatch.authentication.RequestFilter is created");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.log(Level.INFO, "RequestFilter is invoking.");

        String time = request.getParameter("time");
        String sign = request.getParameter("sign");

        /*if (time == null || sign == null) {
            LOGGER.log(Level.SEVERE, "Verify fail");
            return;
        }*/
        boolean status = true;//sign.equals(DigestUtils.md5Hex(time));

        if (status) {
            LOGGER.log(Level.INFO, "Verify result : {0}" ,status);
            chain.doFilter(request, response);
        }
        else {
            LOGGER.log(Level.SEVERE, "Verify fail");
        }
    }
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.authentication.RequestFilter");
}
