package com.huanghehua.www.cinema.app.executor.query;

import com.huanghehua.www.cinema.client.dto.OrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.query.OrderGetQry;
import com.huanghehua.www.cinema.infrastructure.data.OrderPO;
import com.huanghehua.www.cinema.infrastructure.data.SchedulePO;
import com.huanghehua.www.cinema.infrastructure.data.SeatPO;
import com.huanghehua.www.cinema.infrastructure.mapper.OrderMapper;
import com.huanghehua.www.cinema.infrastructure.mapper.ScheduleMapper;
import com.huanghehua.www.cinema.infrastructure.mapper.SeatMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Reference;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 查询单个订单的 executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class OrderGetQryExe {
    @Reference
    private OrderMapper orderMapper;
    @Reference
    private ScheduleMapper scheduleMapper;
    @Reference
    private SeatMapper seatMapper;
    public CommonResult<OrderDetailDTO> execute(OrderGetQry orderGetQry) {
        // 获取订单id
        Long orderId = orderGetQry.getId();

        // 获取订单相关信息
        OrderPO orderPo = orderMapper.getOrderById(orderId);
        Long scheduleId = orderPo.getScheduleId();
        Long seatId = orderPo.getSeatId();

        // 获取场次相关信息
        SchedulePO schedulePo = scheduleMapper.getScheduleById(scheduleId);
        Long filmId = schedulePo.getFilmId();
        Long hallId = schedulePo.getHallId();
        BigDecimal price = schedulePo.getPrice();
        LocalDateTime startTime = schedulePo.getStartTime();
        LocalDateTime endTime = schedulePo.getEndTime();

        // 获取座位相关信息
        SeatPO seatPo = seatMapper.getSeatById(seatId);
        Integer row = seatPo.getRow();
        Integer column = seatPo.getColumn();

        OrderDetailDTO orderDetailDTO =
                new OrderDetailDTO(filmId, hallId, price, startTime, endTime, row, column);

        return CommonResult.operateSuccess(orderDetailDTO);
    }
}

