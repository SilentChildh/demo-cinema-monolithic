package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.client.dto.command.OrderAddCmd;
import com.huanghehua.www.cinema.domain.gateway.OrderGateWay;
import com.huanghehua.www.cinema.infrastructure.gatewayimpl.OrderGateWayImpl;
import com.huanghehua.www.cinema.infrastructure.mapper.SeatMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 订单添加command 的 executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
@Bean
@Interceptable
public class OrderAddCmdExe {

    @Reference(OrderGateWayImpl.class)
    private OrderGateWay orderGateWay;
    @Reference
    private SeatMapper seatMapper;


    /**
     * 座位状态锁，保证状态同步更新
     */
    private static final Object SEAT_STATUS_LOCK = new Object();
    public CommonResult<?> execute(OrderAddCmd orderAddCmd) {
        // 从cmd中获取信息
        Long userId = orderAddCmd.getUserId();
        Long scheduleId = orderAddCmd.getScheduleId();
        Long seatId = orderAddCmd.getSeatId();

        // TODO 这里需要保证座位状态的同步更新，可以考虑使用异步任务完成， 以下的同步还未完成
/*        synchronized (SEAT_STATUS_LOCK) {
            // 将座位更新为占用状态
            seatMapper.updateSeatStatusById(seatId, 1);
        }*/


        // 执行添加订单业务逻辑
        boolean success = orderGateWay.addOrder(userId, scheduleId, seatId);

        return success ? CommonResult.operateSuccess() : CommonResult.operateFail("下单失败， 请重试...");
    }
}
