package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.app.executor.Executor;
import com.huanghehua.www.cinema.client.dto.command.ScheduleRemoveCmd;
import com.huanghehua.www.cinema.infrastructure.mapper.ScheduleMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 安排删除cmd 的executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
@Bean
@Interceptable
public class ScheduleRemoveCmdExe implements Executor<ScheduleRemoveCmd> {
    @Reference
    private ScheduleMapper scheduleMapper;

    @Override
    public CommonResult<?> execute(ScheduleRemoveCmd scheduleRemoveCmd) {
        // 获取场次id
        Long scheduleId = scheduleRemoveCmd.getScheduleId();
        // 执行物理删除操作
        int success = scheduleMapper.deleteScheduleById(scheduleId);

        return success > 0 ?
                CommonResult.operateSuccess() :
                CommonResult.operateFail("上映时间段移除失败...");
    }
}
