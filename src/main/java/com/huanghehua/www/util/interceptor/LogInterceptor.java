package com.huanghehua.www.util.interceptor;


import com.huanghehua.www.aop.annotation.After;
import com.huanghehua.www.aop.annotation.Aspect;
import com.huanghehua.www.aop.annotation.Before;
import com.huanghehua.www.aop.model.Executor;
import com.huanghehua.www.ioc.annotation.Bean;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 日志拦截器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/22
 */
@Aspect
@Bean
public class LogInterceptor {
     private static final Logger logger = Logger.getLogger("com.huanghehua.www.ioc.com.huanghehua.www.dispatch.util.interceptor.LogInterceptor");

    @Before("com.*.*")
    public void printLogBefore(Executor executor) {
        String className = executor.getBean().getClass().getName();
        String methodName = executor.getMethod().getName();
        logger.log(Level.INFO, "{0} 正在执行{1}.{2}业务",
                new Object[]{Thread.currentThread(), className, methodName});
    }

    @After("com.*.*")
    public void printLogAfter(Executor executor) {
        String className = executor.getBean().getClass().getName();
        String methodName = executor.getMethod().getName();
        logger.log(Level.INFO, "{0} {1}.{2}业务执行完毕",
                new Object[]{Thread.currentThread(), className, methodName});
    }

}
