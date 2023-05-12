package com.huanghehua.www.cinema.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 订单历史记录dto
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class HistoryOrderDetailDTO extends OrderDetailDTO {

    /**
     * 历史时间
     */
    private LocalDateTime historyTime;

    public HistoryOrderDetailDTO(Long filmId, Long hallId, BigDecimal price,
                                 LocalDateTime startTime, LocalDateTime endTime,
                                 Integer row, Integer column) {
        super(filmId, hallId, price, startTime, endTime, row, column);
    }

    public HistoryOrderDetailDTO(Long filmId, Long hallId, BigDecimal price,
                                 LocalDateTime startTime, LocalDateTime endTime,
                                 Integer row, Integer column, LocalDateTime historyTime) {
        super(filmId, hallId, price, startTime, endTime, row, column);
        this.historyTime = historyTime;
    }

    public HistoryOrderDetailDTO(LocalDateTime historyTime) {
        this.historyTime = historyTime;
    }

    public HistoryOrderDetailDTO() {
    }

    public LocalDateTime getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(LocalDateTime historyTime) {
        this.historyTime = historyTime;
    }

    @Override
    public String toString() {

        return super.toString() + "OrderHistoryDTO{" +
                "historyTime=" + historyTime +
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
        if (!super.equals(o)) {
            return false;
        }
        HistoryOrderDetailDTO that = (HistoryOrderDetailDTO) o;
        return Objects.equals(historyTime, that.historyTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), historyTime);
    }
}
