package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.client.dto.command.OrderRemoveCmd;
import com.huanghehua.www.cinema.domain.gateway.OrderGateWay;
import com.huanghehua.www.cinema.infrastructure.gatewayimpl.OrderGateWayImpl;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 订单删除命令的executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
@Bean
@Interceptable
public class OrderRemoveCmdExe {


    @Reference(OrderGateWayImpl.class)
    private OrderGateWay orderGateWay;

    /**
     * 根据订单id执行取消订单操作
     *
     * @param orderRemoveCmd 订单删除cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    public CommonResult<?> execute(OrderRemoveCmd orderRemoveCmd) {

        Long orderId = orderRemoveCmd.getOrderId();

        // TODO 还需要考虑退票时间段问题

        orderGateWay.removeOrder(orderId);

        return CommonResult.operateSuccess();
    }
}
