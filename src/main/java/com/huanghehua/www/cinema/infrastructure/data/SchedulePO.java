package com.huanghehua.www.cinema.infrastructure.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 场次持久化对象
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class SchedulePO {
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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public SchedulePO(Long filmId, Long hallId,
                      BigDecimal price, LocalDateTime startTime,
                      LocalDateTime endTime) {
        this.filmId = filmId;
        this.hallId = hallId;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SchedulePO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchedulePO that = (SchedulePO) o;
        return Objects.equals(id, that.id) && Objects.equals(filmId, that.filmId) && Objects.equals(hallId, that.hallId) && Objects.equals(price, that.price) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmId, hallId, price, startTime, endTime, createTime, updateTime);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SchedulePO{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", hallId=" + hallId +
                ", price=" + price +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}

