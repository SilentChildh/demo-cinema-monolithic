package com.huanghehua.www.cinema.client.dto;


import java.util.Objects;

/**
 * 座位信息，用于服务之间传输数据
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class SeatDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 影厅id
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

    public SeatDTO() {
    }

    public SeatDTO(Long id, Long hallId, Integer row, Integer column, Boolean status) {
        this.id = id;
        this.hallId = hallId;
        this.row = row;
        this.column = column;
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
        SeatDTO seatDTO = (SeatDTO) o;
        return Objects.equals(id, seatDTO.id) && Objects.equals(hallId, seatDTO.hallId) && Objects.equals(row, seatDTO.row) && Objects.equals(column, seatDTO.column) && Objects.equals(status, seatDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hallId, row, column, status);
    }

    @Override
    public String toString() {
        return "SeatDTO{" +
                "id=" + id +
                ", hallId=" + hallId +
                ", row=" + row +
                ", column=" + column +
                ", status=" + status +
                '}';
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
}
