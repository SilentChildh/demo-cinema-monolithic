package com.huanghehua.www.cinema.app.service;

import com.huanghehua.www.cinema.domain.model.OrderModel;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

/**
 * 订单执行程序
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
public class OrderServiceImpl {
    @Reference
    private OrderModel orderModel;

    public CommonResult<?> order() {
        orderModel.order();
    }

    public CommonResult<?> getSeat(Long filmId, Long seatId) {

    }

}
