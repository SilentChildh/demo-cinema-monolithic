package com.huanghehua.www.cinema.app.service;

import com.huanghehua.www.cinema.domain.order.model.OrderModel;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
/**
 * 订单执行程序
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
public class OrderServiceImpl {

    public CommonResult<?> order() {
        // 新建Order领域中的order模型
        OrderModel orderModel = new OrderModel();
        orderModel.order();
    }

    public CommonResult<?> getSeat(Long filmId, Long seatId) {

    }

}
