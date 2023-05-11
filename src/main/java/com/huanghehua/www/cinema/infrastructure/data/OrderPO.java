package com.huanghehua.www.cinema.infrastructure.data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 订单持久化对象
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class OrderPO {
    /**
     * id
     */
    private Long id;
    /**
     * 影片id
     */
    private Long filmId;

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
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public OrderPO(Long id, Long filmId, LocalDateTime startTime,
                   LocalDateTime endTime, Integer capacity) {
        this.id = id;
        this.filmId = filmId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public OrderPO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderPO orderPO = (OrderPO) o;
        return Objects.equals(id, orderPO.id) && Objects.equals(filmId, orderPO.filmId) && Objects.equals(startTime, orderPO.startTime) && Objects.equals(endTime, orderPO.endTime) && Objects.equals(capacity, orderPO.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmId, startTime, endTime, capacity);
    }

    @Override
    public String toString() {
        return "OrderPO{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", capacity=" + capacity +
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
