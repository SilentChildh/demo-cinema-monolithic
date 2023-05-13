package com.huanghehua.www.cinema.domain.gateway;

/**
 * 订单网关
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public interface OrderGateWay {
    /**
     * 添加订单
     *
     * @param userId     用户id
     * @param scheduleId 安排id
     * @param seatId     座位id
     * @return boolean
     */
    boolean addOrder(Long userId, Long scheduleId, Long seatId);

    /**
     * 删除订单
     *
     * @param orderId 订单id
     * @return boolean
     */
    boolean removeOrder(Long orderId);


}
