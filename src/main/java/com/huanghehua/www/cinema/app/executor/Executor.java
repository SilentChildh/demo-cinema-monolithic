package com.huanghehua.www.cinema.app.executor;

import com.huanghehua.www.common.CommonResult;

/**
 * command或者query命令的执行器，提供了execute方法
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
public interface Executor<T> {
    /**
     * 获取传入的cmd模型或者qry模型中的数据，执行指定的业务逻辑
     *
     * @param t cmd模型或者qry模型
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> execute(T t);
}
