package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.app.convertor.SeatConvertor;
import com.huanghehua.www.cinema.app.executor.Executor;
import com.huanghehua.www.cinema.client.dto.command.SeatAddCmd;
import com.huanghehua.www.cinema.domain.gateway.SeatGateWay;
import com.huanghehua.www.cinema.domain.seat.SeatModel;
import com.huanghehua.www.cinema.infrastructure.data.SeatPO;
import com.huanghehua.www.cinema.infrastructure.gatewayimpl.SeatGateWayImpl;
import com.huanghehua.www.cinema.infrastructure.mapper.HallMapper;
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
    @Reference(SeatGateWayImpl.class)
    private SeatGateWay seatGateWay;
    @Reference
    private SeatMapper seatMapper;
    @Reference
    private HallMapper hallMapper;

    @Override
    public CommonResult<?> execute(SeatAddCmd seatAddCmd) {
        Long hallId = seatAddCmd.getHallId();
        Integer row = seatAddCmd.getRow();
        Integer column = seatAddCmd.getColumn();

        SeatModel seatModel = seatGateWay.getSeatModel(hallId, row, column);

        // 检查是否不为正数
        if (!seatModel.isPositive()) {
            return CommonResult.operateFail("座位号需要是正数");
        }

        // 检查是否存在该座位
        if (seatModel.isExistence()) {
            return CommonResult.operateFail("已存在该座位");
        }

        // 判断座位是否到达影厅的最大容量
        if (seatModel.isMax()) {
            return CommonResult.operateFail("已到达影厅最大容纳量");
        }

        // 转换为持久化模型
        SeatPO seatPo = SeatConvertor.dtoToPo(seatAddCmd);

        // 执行添加操作
        int success = seatMapper.insertSeat(seatPo);

        return success > 0 ? CommonResult.operateSuccess() : CommonResult.operateFail("添加座位失败...");
    }
}
