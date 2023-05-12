package com.huanghehua.www.cinema.app.executor.query;

import com.huanghehua.www.cinema.client.dto.OrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.query.OrderDetailGetQry;
import com.huanghehua.www.cinema.infrastructure.data.OrderPO;
import com.huanghehua.www.cinema.infrastructure.data.SchedulePO;
import com.huanghehua.www.cinema.infrastructure.data.SeatPO;
import com.huanghehua.www.cinema.infrastructure.mapper.OrderMapper;
import com.huanghehua.www.cinema.infrastructure.mapper.ScheduleMapper;
import com.huanghehua.www.cinema.infrastructure.mapper.SeatMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;


/**
 * 查询单个订单的 executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
@Bean
@Interceptable
public class OrderDetailGetQryExe {
    @Reference
    private OrderMapper orderMapper;
    @Reference
    private ScheduleMapper scheduleMapper;
    @Reference
    private SeatMapper seatMapper;

    public CommonResult<OrderDetailDTO> execute(OrderDetailGetQry orderDetailGetQry) {
        // 获取订单id
        Long orderId = orderDetailGetQry.getId();

        // 创建并填充DTO
        OrderDetailDTO orderDetailDTO = this.fillOrderDetailDTO(new OrderDetailDTO(), orderId);

        return CommonResult.operateSuccess(orderDetailDTO);
    }

    /**
     * 通过订单id，填充传入的DTO中的场次相关信息和座位相关信息，即不包含影片相关信息
     * 最后返回同一引用的DTO
     *
     * @param orderId        订单id
     * @param orderDetailDTO 订单细节dto
     * @return {@link OrderDetailDTO}
     */
    protected OrderDetailDTO fillOrderDetailDTO(OrderDetailDTO orderDetailDTO, Long orderId) {
        // 获取订单相关信息
        OrderPO orderPo = orderMapper.getOrderById(orderId);
        Long scheduleId = orderPo.getScheduleId();
        Long seatId = orderPo.getSeatId();

        // 获取场次相关信息
        SchedulePO schedulePo = scheduleMapper.getScheduleById(scheduleId);
        // 获取座位相关信息
        SeatPO seatPo = seatMapper.getSeatById(seatId);

        orderDetailDTO.setFilmId(schedulePo.getFilmId());
        orderDetailDTO.setHallId(schedulePo.getHallId());
        orderDetailDTO.setPrice(schedulePo.getPrice());
        orderDetailDTO.setStartTime(schedulePo.getStartTime());
        orderDetailDTO.setEndTime(schedulePo.getEndTime());
        orderDetailDTO.setRow(seatPo.getRow());
        orderDetailDTO.setColumn(seatPo.getColumn());

        return orderDetailDTO;
    }
}

