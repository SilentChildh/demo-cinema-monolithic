package com.huanghehua.www.cinema.reservation.infrastructure.data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 座位持久化对象
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class SeatPO {
    /**
     * id
     */
    private Long id;
    /**
     * 影片id
     */
    private Long filmId;
    /**
     * 场次id
     */
    private Long sessionId;
    /**
     * 座位状态，true为被占用，false未被占用
     */
    private Boolean status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public SeatPO(Long id, Long filmId, Long sessionId, Boolean status) {
        this.id = id;
        this.filmId = filmId;
        this.sessionId = sessionId;
        this.status = status;
    }

    public SeatPO() {
    }

    @Override
    public String toString() {
        return "SeatPO{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", sessionId=" + sessionId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
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
        SeatPO seatPO = (SeatPO) o;
        return Objects.equals(id, seatPO.id) && Objects.equals(filmId, seatPO.filmId) && Objects.equals(sessionId, seatPO.sessionId) && Objects.equals(status, seatPO.status) && Objects.equals(createTime, seatPO.createTime) && Objects.equals(updateTime, seatPO.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmId, sessionId, status, createTime, updateTime);
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
