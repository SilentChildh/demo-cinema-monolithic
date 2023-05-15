package com.huanghehua.www.cinema.client.api;

import com.huanghehua.www.cinema.client.dto.OrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.HistoryOrderDetailDTO;
import com.huanghehua.www.cinema.client.dto.ScheduleDTO;
import com.huanghehua.www.cinema.client.dto.SeatDTO;
import com.huanghehua.www.cinema.client.dto.command.OrderAddCmd;
import com.huanghehua.www.cinema.client.dto.command.OrderRemoveCmd;
import com.huanghehua.www.cinema.client.dto.query.OrderDetailGetQry;
import com.huanghehua.www.cinema.client.dto.query.HistoryOrderDetailListQry;
import com.huanghehua.www.common.CommonResult;

import java.util.List;

/**
 * 订单服务接口
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public interface OrderServiceI {
    /**
     * 订单
     *
     * @param orderAddCmd 订单添加cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> order(OrderAddCmd orderAddCmd);

    /**
     * 取消订单
     *
     * @param orderRemoveCmd 订单删除cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> cancel(OrderRemoveCmd orderRemoveCmd);

    /**
     * 根据电影id，显示场次
     *
     * @param filmId 影片id
     * @return {@link CommonResult}<{@link List}<{@link ScheduleDTO}>>
     */
    CommonResult<List<ScheduleDTO>> showSchedule(Long filmId);

    /**
     * 显示活跃座位
     *
     * @param scheduleId 场次id
     * @return {@link CommonResult}<{@link List}<{@link SeatDTO}>>
     */
    CommonResult<List<SeatDTO>> showActiveSeat(Long scheduleId);

    /**
     * 显示订单信息
     *
     * @param orderDetailGetQry 查询订单dto
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<OrderDetailDTO> showOrderDetail(OrderDetailGetQry orderDetailGetQry);

    /**
     * 获取订单历史列表
     *
     * @param historyOrderDetailListQry 历史订单细节qry列表
     * @return {@link CommonResult}<{@link List}<{@link HistoryOrderDetailDTO}>>
     */
    CommonResult<List<HistoryOrderDetailDTO>> showListOrderHistory(HistoryOrderDetailListQry historyOrderDetailListQry);
}
