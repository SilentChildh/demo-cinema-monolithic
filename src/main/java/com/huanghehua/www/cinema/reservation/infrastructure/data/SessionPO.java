package com.huanghehua.www.cinema.reservation.infrastructure.data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 场次持久化对象
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class SessionPO {
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

    public SessionPO(Long id, Long filmId, LocalDateTime startTime, LocalDateTime endTime, Integer capacity,
                     LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.filmId = filmId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    public SessionPO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionPO sessionPO = (SessionPO) o;
        return Objects.equals(id, sessionPO.id) && Objects.equals(filmId, sessionPO.filmId) && Objects.equals(startTime, sessionPO.startTime) && Objects.equals(endTime, sessionPO.endTime) && Objects.equals(capacity, sessionPO.capacity) && Objects.equals(createTime, sessionPO.createTime) && Objects.equals(updateTime, sessionPO.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmId, startTime, endTime, capacity, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "SessionPO{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", capacity=" + capacity +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
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

