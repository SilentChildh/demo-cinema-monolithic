package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.client.dto.command.OrderRemoveCmd;
import com.huanghehua.www.cinema.domain.gateway.OrderGateWay;
import com.huanghehua.www.cinema.infrastructure.gatewayimpl.OrderGateWayImpl;
import com.huanghehua.www.cinema.infrastructure.mapper.OrderMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    @Reference
    private OrderMapper orderMapper;

    /**
     * 根据订单id执行取消订单操作
     *
     * @param orderRemoveCmd 订单删除cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    public CommonResult<?> execute(OrderRemoveCmd orderRemoveCmd) {

        Long orderId = orderRemoveCmd.getOrderId();

        // 检查订单有效状态
        Boolean status = orderMapper.getStatusById(orderId);
        if (!status) {
            return CommonResult.operateFail("不存在该订单");
        }

        // 检查是否超时
        LocalDateTime createTime = orderMapper.getCreateTimeById(orderId);
        // 三十分钟的时限
        final int timeOut = 30;
        // 若超时则不可取消
        if (createTime.plus(timeOut, ChronoUnit.MINUTES).isBefore(LocalDateTime.now())) {
            return CommonResult.operateFail("已超三十分钟，不可取消订单...");
        }

        // 执行取消订单操作
        orderGateWay.removeOrder(orderId);
        return CommonResult.operateSuccess();
    }
}
