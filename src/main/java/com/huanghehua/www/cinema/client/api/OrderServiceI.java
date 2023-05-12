package com.huanghehua.www.cinema.client.api;

import com.huanghehua.www.cinema.client.dto.command.OrderAddCmd;
import com.huanghehua.www.common.CommonResult;

/**
 * 订单服务接口
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public interface OrderServiceI {
    /**
     * 订单
     *
     * @param orderAddCmd 订单添加cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> order(OrderAddCmd orderAddCmd);

    /**
     * 根据电影id，显示场次
     *
     * @param filmId 影片id
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> showSchedule(Long filmId);

    /**
     * 显示活跃座位
     *
     * @param scheduleId 场次id
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> showActiveSeat(Long scheduleId);
}
