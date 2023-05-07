package com.huanghehua.www.cinema.reservation.domain;

import com.huanghehua.www.cinema.reservation.domain.gateway.OrderGateWay;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

/**
 * 订单
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
public class OrderDomain {

    /**
     * 上映场次域
     */
    private SessionDomain sessionDomain;
    /**
     * 座位域
     */
    private SeatDomain seatDomain;

    /**
     * 订单网关
     */
    @Reference
    private OrderGateWay orderGateWay;
    /**
     * order方法的锁
     */
    private static final Object LOCK_FOR_ORDER = new Object();
    public boolean order(SessionDomain sessionDomain, SeatDomain seatDomain) {
        synchronized (LOCK_FOR_ORDER) {
                // TODO 利用GateWay进行订票
                return orderGateWay.doOrder();
        }
    }

    public OrderDomain getSeat() {
    }

}
