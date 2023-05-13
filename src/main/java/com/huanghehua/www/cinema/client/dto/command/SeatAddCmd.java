package com.huanghehua.www.cinema.client.dto.command;

import java.util.Objects;

/**
 * 座位添加cmd
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
public class SeatAddCmd {
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

    public SeatAddCmd() {
    }

    public SeatAddCmd(Long hallId, Integer row, Integer column) {
        this.hallId = hallId;
        this.row = row;
        this.column = column;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeatAddCmd that = (SeatAddCmd) o;
        return Objects.equals(hallId, that.hallId) && Objects.equals(row, that.row) && Objects.equals(column, that.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hallId, row, column);
    }

    @Override
    public String toString() {
        return "SeatAddCmd{" +
                "hallId=" + hallId +
                ", row=" + row +
                ", column=" + column +
                '}';
    }

}
