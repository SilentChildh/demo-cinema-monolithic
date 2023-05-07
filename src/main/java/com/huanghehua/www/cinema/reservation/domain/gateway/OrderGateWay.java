package com.huanghehua.www.cinema.reservation.domain.gateway;

import com.huanghehua.www.cinema.reservation.domain.SessionDomain;

import java.util.List;

/**
 * 订单网关
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public interface OrderGateWay {
    /**
     * 执行订票
     *
     * @return boolean
     */
    boolean doOrder();

    /**
     * 根据电影id获取场次信息
     *
     * @param filmId 影片id
     * @return {@link List}<{@link SessionDomain}>
     */
    List<SessionDomain> getSessions(Long filmId);
}
