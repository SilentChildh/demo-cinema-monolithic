package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.app.service.OrderServiceImpl;
import com.huanghehua.www.cinema.client.dto.OrderDTO;
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
    @Reference(OrderServiceImpl.class)
    private OrderServiceImpl orderServiceImpl;


    @Request(value = "/order", method = "post")
    public CommonResult<?> order(OrderDTO orderDTO) {
         return orderServiceImpl.order();
    }

    @Request(value = "/info", method = "get")
    public CommonResult<?> orderInfo(OrderDTO orderDTO) {
        return orderServiceImpl.order();
    }

}
