package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.app.executor.Executor;
import com.huanghehua.www.cinema.client.dto.command.SeatRemoveCmd;
import com.huanghehua.www.cinema.infrastructure.mapper.SeatMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 座位移除cmd exe
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/15
 */
@Bean
@Interceptable
public class SeatRemoveCmdExe implements Executor<SeatRemoveCmd> {
    @Reference
    private SeatMapper seatMapper;
    @Override
    public CommonResult<?> execute(SeatRemoveCmd seatRemoveCmd) {
        Long seatId = seatRemoveCmd.getSeatId();
        int success = seatMapper.deleteSeatById(seatId);
        return success > 0 ? CommonResult.operateSuccess() : CommonResult.operateFail("移除失败...");
    }
}
