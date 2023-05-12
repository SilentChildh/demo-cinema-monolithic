package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.app.service.OrderServiceImpl;
import com.huanghehua.www.cinema.client.api.OrderServiceI;
import com.huanghehua.www.cinema.client.dto.OrderDTO;
import com.huanghehua.www.cinema.client.dto.command.OrderAddCmd;
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
    private OrderServiceI orderService;

    /**
     * 下订单
     *
     * @param orderAddCmd 订单添加cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/order", method = "post")
    public CommonResult<?> order(OrderAddCmd orderAddCmd) {

         return orderService.order(orderAddCmd);
    }

    @Request(value = "/info", method = "get")
    public CommonResult<?> orderInfo(OrderDTO orderDTO) {
        // 如果方法参数是qry，则将dto组装为qry，在传入接口方法中
        return orderService.order();
    }

}
