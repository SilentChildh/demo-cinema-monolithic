package com.huanghehua.www.cinema.infrastructure.data;

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
     * 影厅id, 唯一id
     */
    private Long hallId;
    /**
     * 行号
     */
    private Integer row;
    /**
     * 列号
     */
    private Integer column;

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

    public SeatPO(Long id, Long hallId, Integer row, Integer column, Boolean status) {
        this.id = id;
        this.hallId = hallId;
        this.row = row;
        this.column = column;
        this.status = status;
    }

    public SeatPO(Long hallId, Integer row, Integer column) {
        this.hallId = hallId;
        this.row = row;
        this.column = column;
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
        return Objects.equals(id, seatPO.id) && Objects.equals(hallId, seatPO.hallId) && Objects.equals(row, seatPO.row) && Objects.equals(column, seatPO.column) && Objects.equals(status, seatPO.status) && Objects.equals(createTime, seatPO.createTime) && Objects.equals(updateTime, seatPO.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hallId, row, column, status, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "SeatPO{" +
                "id=" + id +
                ", hallId=" + hallId +
                ", row=" + row +
                ", column=" + column +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public SeatPO() {
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
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
