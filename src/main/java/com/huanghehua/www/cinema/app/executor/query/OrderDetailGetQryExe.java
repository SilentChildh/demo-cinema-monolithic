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

    /**
     * 通过订单id获取订单详情<br/>
     * <p/>
     * 当订单失效时，返回失败结果
     *
     * @param orderDetailGetQry 订单细节得到qry
     * @return {@link CommonResult}<{@link OrderDetailDTO}>
     */
    public CommonResult<OrderDetailDTO> execute(OrderDetailGetQry orderDetailGetQry) {
        // 获取订单id，并获取订单持久化对象
        Long orderId = orderDetailGetQry.getId();
        OrderPO orderPo = orderMapper.getOrderById(orderId);

        if (!orderPo.getStatus()) {
            return CommonResult.operateFail("订单失效，请重新尝试...");
        }

        // 创建并填充DTO
        OrderDetailDTO orderDetailDTO = this.fillOrderDetailDTO(new OrderDetailDTO(), orderPo);

        return CommonResult.operateSuccess(orderDetailDTO);
    }

    /**
     * 通过订单持久化对象，填充传入的DTO中的场次相关信息和座位相关信息，即不包含影片相关信息
     * 最后返回同一引用的DTO
     *
     * @param orderDetailDTO 订单细节dto
     * @param orderPo        订单订单
     * @return {@link OrderDetailDTO}
     */
    protected OrderDetailDTO fillOrderDetailDTO(OrderDetailDTO orderDetailDTO, OrderPO orderPo) {
        // 获取订单相关信息
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

