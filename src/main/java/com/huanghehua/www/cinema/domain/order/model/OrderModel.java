package com.huanghehua.www.cinema.domain.order.model;

import com.huanghehua.www.cinema.domain.gateway.OrderGateWay;
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
public class OrderModel {

    /**
     * 上映场次域
     */
    private SessionModel sessionModel;
    /**
     * 座位域
     */
    private SeatModel seatModel;

    /**
     * 订单网关
     */
    @Reference
    private OrderGateWay orderGateWay;
    /**
     * order方法的锁
     */
    private static final Object LOCK_FOR_ORDER = new Object();
    public boolean order(SessionModel sessionModel, SeatModel seatModel) {
        synchronized (LOCK_FOR_ORDER) {
                // TODO 利用GateWay进行订票
                return orderGateWay.doOrder();
        }
    }

}
