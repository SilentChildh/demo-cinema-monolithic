package com.huanghehua.www.cinema.client.dto.command;

import java.util.Objects;

/**
 * 座位移除cmd
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/15
 */
public class SeatRemoveCmd {
    /**
     * 影厅id
     * TODO 可能不需要这个字段
     */
    private Long hallId;
    /**
     * 座位id
     */
    private Long seatId;

    public SeatRemoveCmd() {
    }

    public SeatRemoveCmd(Long hallId, Long seatId) {
        this.hallId = hallId;
        this.seatId = seatId;
    }

    @Override
    public String toString() {
        return "SeatRemoveCmd{" +
                "hallId=" + hallId +
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
        SeatRemoveCmd that = (SeatRemoveCmd) o;
        return Objects.equals(hallId, that.hallId) && Objects.equals(seatId, that.seatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hallId, seatId);
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
}
