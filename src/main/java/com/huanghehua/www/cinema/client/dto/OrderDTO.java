package com.huanghehua.www.cinema.client.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 电影订单数据模型
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class OrderDTO {
    /**
     * 影片id
     */
    private Long filmId;
    /**
     * 场次id
     */
    private Long sessionId;
    /**
     * 座位id
     */
    private Long seatId;
    /**
     * 影片场次开始时间
     */
    private LocalDateTime startTime;
    /**
     * 影片场次结束时间
     */
    private LocalDateTime endTime;
    /**
     * 场次可容纳实际人数
     */
    private Integer capacity;
    /**
     * 座位状态
     */
    private Boolean status;

    public OrderDTO(Long filmId, Long sessionId, Long seatId) {
        this.filmId = filmId;
        this.sessionId = sessionId;
        this.seatId = seatId;
    }

    public OrderDTO() {
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "filmId=" + filmId +
                ", sessionId=" + sessionId +
                ", seatId=" + seatId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(filmId, orderDTO.filmId) && Objects.equals(sessionId, orderDTO.sessionId) && Objects.equals(seatId, orderDTO.seatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, sessionId, seatId);
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
}
