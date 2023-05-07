package com.huanghehua.www.cinema.reservation.domain;

import com.huanghehua.www.cinema.reservation.domain.gateway.OrderGateWay;
import com.huanghehua.www.cinema.reservation.infrastructure.gatewayimpl.OrderGateWayImpl;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 上映场次域
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
public class SessionDomain {
    /**
     * 影片id
     */
    private Long filmId;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    public SessionDomain(Long filmId, LocalDateTime startTime,
                         LocalDateTime endTime) {
        this.filmId = filmId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SessionDomain() {
    }

    @Reference(OrderGateWayImpl.class)
    private OrderGateWay orderGateWay;

    /**
     * 通过电影名称获取场次
     *
     * @param filmId 影片id
     * @return {@link List}<{@link SessionDomain}>
     */
    public List<SessionDomain> getSessions(Long filmId) {
        return orderGateWay.getSessions(filmId);
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }
}
