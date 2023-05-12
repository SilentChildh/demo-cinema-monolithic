package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.client.dto.command.OrderRemoveCmd;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
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



    public CommonResult<?> execute(OrderRemoveCmd orderRemoveCmd) {

        // TODO
        Long orderId = orderRemoveCmd.getOrderId();



        return CommonResult.operateSuccess();
    }
}
