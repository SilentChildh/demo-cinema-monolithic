package com.huanghehua.www.cinema.infrastructure.data;

import java.math.BigDecimal;
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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public OrderPO(Long id, Long userId, Long scheduleId) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
    }

    public OrderPO(Long id, Long userId, Long scheduleId, BigDecimal price) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.price = price;
    }

    public OrderPO(Long id, Long userId, Long scheduleId, Long seatId, BigDecimal price) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.price = price;
    }

    public OrderPO(Long id, Long userId, Long scheduleId,
                   LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OrderPO() {
    }

    public OrderPO(Long id, Long userId, Long scheduleId, Long seatId,
                   BigDecimal price, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.price = price;
        this.createTime = createTime;
        this.updateTime = updateTime;
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
        return Objects.equals(id, orderPO.id) && Objects.equals(userId, orderPO.userId) && Objects.equals(scheduleId, orderPO.scheduleId) && Objects.equals(seatId, orderPO.seatId) && Objects.equals(price, orderPO.price) && Objects.equals(createTime, orderPO.createTime) && Objects.equals(updateTime, orderPO.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, scheduleId, seatId, price, createTime, updateTime);
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

    @Override
    public String toString() {
        return "OrderPO{" +
                "id=" + id +
                ", userId=" + userId +
                ", scheduleId=" + scheduleId +
                ", seatId=" + seatId +
                ", price=" + price +
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
