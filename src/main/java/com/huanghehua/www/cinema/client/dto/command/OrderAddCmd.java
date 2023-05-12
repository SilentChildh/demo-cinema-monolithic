package com.huanghehua.www.cinema.client.dto.command;

import java.util.Objects;

/**
 * 订单添加command
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class OrderAddCmd {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 场次id
     */
    private Long scheduleId;
    /**
     * 座位id
     */
    private Long seatId;

    public OrderAddCmd(Long userId, Long scheduleId, Long seatId) {
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
    }

    public OrderAddCmd() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderAddCmd that = (OrderAddCmd) o;
        return Objects.equals(userId, that.userId) && Objects.equals(scheduleId, that.scheduleId) && Objects.equals(seatId, that.seatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, scheduleId, seatId);
    }

    @Override
    public String toString() {
        return "OrderAddCmd{" +
                "userId=" + userId +
                ", scheduleId=" + scheduleId +
                ", seatId=" + seatId +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
}
