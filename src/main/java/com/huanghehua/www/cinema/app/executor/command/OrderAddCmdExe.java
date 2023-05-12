package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.client.dto.command.OrderAddCmd;
import com.huanghehua.www.cinema.domain.gateway.OrderGateWay;
import com.huanghehua.www.cinema.infrastructure.gatewayimpl.OrderGateWayImpl;
import com.huanghehua.www.cinema.infrastructure.mapper.SeatMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

import java.util.Arrays;

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
     * 座位状态分段锁，保证状态同步更新
     */
    private static final Object[] SEAT_STATUS_LOCKS = new Object[100];
    static {
        // 填充分段锁
        Arrays.fill(SEAT_STATUS_LOCKS, new Object());
    }

    /**
     * 利用分段锁{@code SEAT_STATUS_LOCKS}保证， 座位的同步更新
     *
     * @param orderAddCmd 订单添加cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    public CommonResult<?> execute(OrderAddCmd orderAddCmd) {
        // 从cmd中获取信息
        Long userId = orderAddCmd.getUserId();
        Long scheduleId = orderAddCmd.getScheduleId();
        Long seatId = orderAddCmd.getSeatId();

        // 利用分段锁，分别处理不同场次的订单
        synchronized (SEAT_STATUS_LOCKS[Math.toIntExact(scheduleId)]) {
            // 先判断座位是否被占用
            Boolean seatStatus = seatMapper.getStatusById(seatId);
            // 空闲则修改为占用状态
            if (!seatStatus) {
                seatMapper.updateSeatStatusById(seatId, 1);
            }
            // 否则直接返回失败
            else {
                return CommonResult.operateFail("座位已占用， 请选择其他座位...");
            }
        }

        // 执行添加订单业务逻辑
        boolean success = orderGateWay.addOrder(userId, scheduleId, seatId);
        return success ? CommonResult.operateSuccess() : CommonResult.operateFail("下单失败， 请重试...");
    }
}
