package com.huanghehua.www.cinema.reservation.infrastructure.gatewayimpl;

import com.huanghehua.www.cinema.reservation.domain.SessionDomain;
import com.huanghehua.www.cinema.reservation.domain.gateway.OrderGateWay;
import com.huanghehua.www.cinema.reservation.infrastructure.data.SessionPO;
import com.huanghehua.www.cinema.reservation.infrastructure.mapper.OrderMapper;
import com.huanghehua.www.cinema.reservation.infrastructure.mapper.SessionMapper;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单网关实现类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
public class OrderGateWayImpl implements OrderGateWay {
    @Reference
    private OrderMapper orderMapper;
    @Reference
    private SessionMapper sessionMapper;
    @Override
    public boolean doOrder() {
        return orderMapper.insertOrder() > 0;
    }

    @Override
    public List<SessionDomain> getSessions(Long filmId) {

        ArrayList<SessionDomain> sessionDomains = new ArrayList<>(12);
        List<SessionPO> sessions = sessionMapper.selectListByFilmId(filmId);

        for (SessionPO session : sessions) {
            LocalDateTime startTime = session.getStartTime();
            LocalDateTime endTime = session.getEndTime();

            SessionDomain sessionDomain = new SessionDomain(filmId, startTime, endTime);

            sessionDomains.add(sessionDomain);
        }

        return sessionDomains;
    }
}
