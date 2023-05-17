package com.huanghehua.www.cinema.domain.seat;

import java.util.Objects;

/**
 * 座位模型
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/17
 */
public class SeatModel {
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
     * 影厅容量
     */
    private Integer capacity;
    /**
     * 目前上架的座位
     */
    private Long presentSeats;

    /**
     * 已存在该座位
     */
    private Boolean existence;

    public SeatModel(Long hallId, Integer row, Integer column) {
        this.hallId = hallId;
        this.row = row;
        this.column = column;
    }

    public boolean isExistence() {
        return existence;
    }

    /**
     * 判断座位号为正数
     *
     * @return boolean
     */
    public boolean isPositive() {
        return row > 0 && column > 0;
    }

    /**
     * 判断座位是否到达影厅的最大容量
     *
     * @return boolean
     */
    public boolean isMax() {
        return capacity.compareTo(Math.toIntExact(presentSeats)) == 0;
    }

    public SeatModel() {
    }

    public SeatModel(Long id, Long hallId, Integer row, Integer column, Boolean status,
                     Integer capacity, Long presentSeats, Boolean existence) {
        this.id = id;
        this.hallId = hallId;
        this.row = row;
        this.column = column;
        this.status = status;
        this.capacity = capacity;
        this.presentSeats = presentSeats;
        this.existence = existence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeatModel seatModel = (SeatModel) o;
        return Objects.equals(id, seatModel.id) && Objects.equals(hallId, seatModel.hallId) && Objects.equals(row, seatModel.row) && Objects.equals(column, seatModel.column) && Objects.equals(status, seatModel.status) && Objects.equals(capacity, seatModel.capacity) && Objects.equals(presentSeats, seatModel.presentSeats) && Objects.equals(existence, seatModel.existence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hallId, row, column, status, capacity, presentSeats, existence);
    }

    @Override
    public String toString() {
        return "SeatModel{" +
                "id=" + id +
                ", hallId=" + hallId +
                ", row=" + row +
                ", column=" + column +
                ", status=" + status +
                ", capacity=" + capacity +
                ", presentSeats=" + presentSeats +
                ", existence=" + existence +
                '}';
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getPresentSeats() {
        return presentSeats;
    }

    public void setPresentSeats(Long presentSeats) {
        this.presentSeats = presentSeats;
    }

    public Boolean getExistence() {
        return existence;
    }

    public void setExistence(Boolean existence) {
        this.existence = existence;
    }
}
