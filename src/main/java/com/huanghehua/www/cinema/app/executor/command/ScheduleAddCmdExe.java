package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.app.convertor.ScheduleConvertor;
import com.huanghehua.www.cinema.client.dto.command.ScheduleAddCmd;
import com.huanghehua.www.cinema.infrastructure.data.SchedulePO;
import com.huanghehua.www.cinema.infrastructure.mapper.ScheduleMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 计划添加cmd 的executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
@Bean
@Interceptable
public class ScheduleAddCmdExe {

    @Reference
    private ScheduleMapper scheduleMapper;


    public CommonResult<?> execute(ScheduleAddCmd scheduleAddCmd) {
        // 转化为持久化对象
        SchedulePO schedulePo = ScheduleConvertor.dtoToPo(scheduleAddCmd);
        // 执行添加操作
        int success = scheduleMapper.insertSchedule(schedulePo);

        return success > 0 ?
                CommonResult.operateSuccess() :
                CommonResult.operateFail("添加电影场次失败");
    }
}
