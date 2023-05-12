package com.huanghehua.www.cinema.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 场次信息dto
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class ScheduleDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 影片id
     */
    private Long filmId;
    /**
     * 影厅id
     */
    private Long hallId;
    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 影片场次开始时间
     */
    private LocalDateTime startTime;
    /**
     * 影片场次结束时间
     */
    private LocalDateTime endTime;

    public ScheduleDTO(Long id, Long filmId,
                       Long hallId, BigDecimal price,
                       LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.filmId = filmId;
        this.hallId = hallId;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ScheduleDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScheduleDTO that = (ScheduleDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(filmId, that.filmId) && Objects.equals(hallId, that.hallId) && Objects.equals(price, that.price) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmId, hallId, price, startTime, endTime);
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", hallId=" + hallId +
                ", price=" + price +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
}
