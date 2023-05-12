package com.huanghehua.www.cinema.infrastructure.gatewayimpl;

import com.huanghehua.www.cinema.domain.gateway.OrderGateWay;
import com.huanghehua.www.cinema.infrastructure.mapper.OrderMapper;
import com.huanghehua.www.cinema.infrastructure.mapper.ScheduleMapper;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

import java.math.BigDecimal;

/**
 * 订单网关实现类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
public class OrderGateWayImpl implements OrderGateWay {

    @Reference
    private OrderMapper orderMapper;
    @Reference
    private ScheduleMapper scheduleMapper;


    @Override
    public boolean addOrder(Long userId, Long scheduleId, Long seatId) {
        // 先从场次Mapper获取价格，以便保持价一致
        BigDecimal price = scheduleMapper.getPriceById(scheduleId);

        return orderMapper.insertOrder(userId, scheduleId, seatId, price) > 0;
    }

    @Override
    public boolean removeOrder(Long orderId) {
        orderMapper.
    }
}
