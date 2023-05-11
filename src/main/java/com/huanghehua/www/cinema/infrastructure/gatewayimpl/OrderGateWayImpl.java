package com.huanghehua.www.cinema.infrastructure.gatewayimpl;

import com.huanghehua.www.cinema.domain.model.SessionModel;
import com.huanghehua.www.cinema.domain.gateway.OrderGateWay;
import com.huanghehua.www.cinema.infrastructure.data.SessionPO;
import com.huanghehua.www.cinema.infrastructure.mapper.OrderMapper;
import com.huanghehua.www.cinema.infrastructure.mapper.SessionMapper;
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
    public List<SessionModel> getSessions(Long filmId) {

        ArrayList<SessionModel> sessionModels = new ArrayList<>(12);
        List<SessionPO> sessions = sessionMapper.selectListByFilmId(filmId);

        for (SessionPO session : sessions) {
            LocalDateTime startTime = session.getStartTime();
            LocalDateTime endTime = session.getEndTime();

            SessionModel sessionModel = new SessionModel(filmId, startTime, endTime);

            sessionModels.add(sessionModel);
        }

        return sessionModels;
    }
}
