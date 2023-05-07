package com.huanghehua.www.cinema.reservation.app;

import com.huanghehua.www.cinema.reservation.domain.OrderDomain;
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
public class OrderExecutor {
    @Reference
    private OrderDomain orderDomain;

    public CommonResult<?> order() {
        orderDomain.order();
    }

    public CommonResult<?> getSeat(Long filmId, Long seatId) {

    }

}
