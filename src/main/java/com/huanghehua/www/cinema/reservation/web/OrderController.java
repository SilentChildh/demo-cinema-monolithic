package com.huanghehua.www.cinema.reservation.web;

import com.huanghehua.www.cinema.reservation.app.OrderExecutor;
import com.huanghehua.www.cinema.reservation.web.data.OrderVO;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

/**
 * 订单控制器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
@Request("/reservation")
public class OrderController {
    @Reference(OrderExecutor.class)
    private OrderExecutor orderExecutor;


    @Request(value = "/order", method = "post")
    public CommonResult<?> order(OrderVO orderVo) {
         return orderExecutor.order();
    }

    @Request(value = "/info", method = "get")
    public CommonResult<?> orderInfo(OrderVO orderVo) {
        return orderExecutor.order();
    }

}
