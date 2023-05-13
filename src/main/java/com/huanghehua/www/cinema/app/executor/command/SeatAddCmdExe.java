package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.app.convertor.SeatConvertor;
import com.huanghehua.www.cinema.app.executor.Executor;
import com.huanghehua.www.cinema.client.dto.command.SeatAddCmd;
import com.huanghehua.www.cinema.infrastructure.data.SeatPO;
import com.huanghehua.www.cinema.infrastructure.mapper.SeatMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 座位添加cmd 的executor
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
@Bean
@Interceptable
public class SeatAddCmdExe implements Executor<SeatAddCmd> {
    @Reference
    private SeatMapper seatMapper;

    @Override
    public CommonResult<?> execute(SeatAddCmd seatAddCmd) {
        // 转换为持久化模型
        SeatPO seatPo = SeatConvertor.dtoToPo(seatAddCmd);

        // 执行添加操作
        int success = seatMapper.insertSeat(seatPo);

        return success > 0 ? CommonResult.operateSuccess() : CommonResult.operateFail("添加座位失败...");
    }
}
