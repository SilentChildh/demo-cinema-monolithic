package com.huanghehua.www.cinema.client.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 电影订单数据模型
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class OrderDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 场次id
     */
    private Long scheduleId;
    /**
     * 座位id
     */
    private Long seatId;
    /**
     * 价格
     */
    private BigDecimal price;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Long userId, Long scheduleId, Long seatId, BigDecimal price) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", scheduleId=" + scheduleId +
                ", seatId=" + seatId +
                ", price=" + price +
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
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(id, orderDTO.id) && Objects.equals(userId, orderDTO.userId) && Objects.equals(scheduleId, orderDTO.scheduleId) && Objects.equals(seatId, orderDTO.seatId) && Objects.equals(price, orderDTO.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, scheduleId, seatId, price);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
}
