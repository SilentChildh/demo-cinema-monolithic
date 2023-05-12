package com.huanghehua.www.cinema.infrastructure.mapper;

import com.huanghehua.www.orm.annotation.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单历史映射器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public interface HistoryOrderMapper {

    /**
     * 通过用户id获取订单id列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link Long}>
     */
    List<Long> listOrderIdByUserId(@Param("userId") Long userId);

    /**
     * 通过订单id获取历史记录更新时间
     *
     * @param orderId 订单id
     * @return {@link LocalDateTime}
     */
    LocalDateTime getUpdateTimeByOrderId(@Param("orderId") Long orderId);
}
