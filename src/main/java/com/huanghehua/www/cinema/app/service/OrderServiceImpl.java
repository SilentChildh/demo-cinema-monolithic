package com.huanghehua.www.cinema.app.service;

import com.huanghehua.www.cinema.app.convertor.ScheduleConvertor;
import com.huanghehua.www.cinema.app.convertor.SeatConvertor;
import com.huanghehua.www.cinema.app.executor.command.OrderAddCmdExe;
import com.huanghehua.www.cinema.app.executor.command.OrderRemoveCmdExe;
import com.huanghehua.www.cinema.app.executor.query.OrderDetailGetQryExe;
import com.huanghehua.www.cinema.app.executor.query.HistoryOrderDetailListQryExe;
import com.huanghehua.www.cinema.client.api.OrderServiceI;
import com.huanghehua.www.cinema.client.dto.OrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.HistoryOrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.ScheduleDTO;
import com.huanghehua.www.cinema.client.dto.SeatDTO;
import com.huanghehua.www.cinema.client.dto.command.OrderAddCmd;
import com.huanghehua.www.cinema.client.dto.command.OrderRemoveCmd;
import com.huanghehua.www.cinema.client.dto.query.OrderDetailGetQry;
import com.huanghehua.www.cinema.client.dto.query.HistoryOrderDetailListQry;
import com.huanghehua.www.cinema.infrastructure.data.SchedulePO;
import com.huanghehua.www.cinema.infrastructure.data.SeatPO;
import com.huanghehua.www.cinema.infrastructure.mapper.ScheduleMapper;
import com.huanghehua.www.cinema.infrastructure.mapper.SeatMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

import java.util.List;

/**
 * 订单执行程序
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
@Interceptable
public class OrderServiceImpl implements OrderServiceI {

    @Reference
    private OrderAddCmdExe orderAddCmdExe;
    @Reference
    private OrderRemoveCmdExe orderRemoveCmdExe;
    @Reference
    private OrderDetailGetQryExe orderDetailGetQryExe;
    @Reference
    private HistoryOrderDetailListQryExe historyOrderDetailListQryExe;
    @Reference
    private ScheduleMapper scheduleMapper;
    @Reference
    private SeatMapper seatMapper;

    @Override
    public CommonResult<?> order(OrderAddCmd orderAddCmd) {
        return orderAddCmdExe.execute(orderAddCmd);
    }

    @Override
    public CommonResult<?> cancel(OrderRemoveCmd orderRemoveCmd) {
        return orderRemoveCmdExe.execute(orderRemoveCmd);
    }

    @Override
    public CommonResult<List<ScheduleDTO>> showSchedule(Long filmId) {
        List<SchedulePO> schedulePoList = scheduleMapper.listByFilmId(filmId);

        // 利用转换器转换为dto模型
        List<ScheduleDTO> list = ScheduleConvertor.poListToDto(schedulePoList);
        return CommonResult.operateSuccess(list);
    }

    @Override
    public CommonResult<List<SeatDTO>> showActiveSeat(Long scheduleId) {
        SchedulePO schedulePo = scheduleMapper.getScheduleById(scheduleId);

        // 获取影厅id，再获取座位表
        Long hallId = schedulePo.getHallId();

        List<SeatPO> seatPoList = seatMapper.listActiveSeatByHallId(hallId);

        // 利用转换器转换为dto模型
        List<SeatDTO> list = SeatConvertor.poListToDto(seatPoList);
        return CommonResult.operateSuccess(list);
    }

    @Override
    public CommonResult<OrderDetailDTO> showOrderDetail(OrderDetailGetQry orderDetailGetQry) {
        return orderDetailGetQryExe.execute(orderDetailGetQry);
    }

    @Override
    public CommonResult<List<HistoryOrderDetailDTO>> showListOrderHistory(HistoryOrderDetailListQry historyOrderDetailListQry) {
        return historyOrderDetailListQryExe.execute(historyOrderDetailListQry);
    }
}
