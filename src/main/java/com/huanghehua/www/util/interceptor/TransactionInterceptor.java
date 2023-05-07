package com.huanghehua.www.util.interceptor;



import com.huanghehua.www.aop.annotation.Around;
import com.huanghehua.www.aop.annotation.Aspect;
import com.huanghehua.www.aop.model.Executor;
import com.huanghehua.www.datasource.exception.DataSourceException;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.orm.SqlSession;
import com.huanghehua.www.orm.util.SimpleSqlSessionUtil;
import com.huanghehua.www.util.interceptor.exception.UnknownException;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 事务拦截器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/22
 */
@Aspect
@Bean
public class TransactionInterceptor {

    @Around("com.*.*")
    public Object transactionHandle(Executor executor) {

        LOGGER.log(Level.INFO, "TransactionHandleInterceptor is being invoked");
        Object invoke;
        SqlSession sqlSession = null;
        try {
            sqlSession = SimpleSqlSessionUtil.openSession();

            Object bean = executor.getBean();
            Method method = executor.getMethod();
            Object[] args = executor.getArgs();
            invoke = method.invoke(bean, args);

            sqlSession.commit();
            LOGGER.log(Level.INFO, "{0} 提交事务", Thread.currentThread());
        }
        // 需要注意这里的异常类型，必须得是Exception，否则可能无法捕获其他异常，而导致回滚失败
        catch (Exception e) {
            try {
                if (sqlSession != null) {
                    sqlSession.rollback();
                    LOGGER.log(Level.INFO, "Transaction rollback successfully");
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Transaction rollback exception", ex);
                throw new DataSourceException("Transaction rollback exception", ex);
            }

            LOGGER.log(Level.WARNING, Thread.currentThread() + " 事务异常", e);
            throw new UnknownException(Thread.currentThread() + " 事务异常", e);
        } finally {
            try {
                if (sqlSession != null) {
                    sqlSession.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Connection close exception", e);
                throw new DataSourceException("Connection close exception", e);
            }
        }

        LOGGER.log(Level.INFO, "TransactionHandleInterceptor is done");
        return invoke;
    }

    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.ioc.com.huanghehua.www.dispatch.util.interceptor.TransactionInterceptor");
}
