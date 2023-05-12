package com.huanghehua.www.cinema.infrastructure.data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 订单历史持久化对象
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class HistoryOrderPO {
    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 创建的时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public HistoryOrderPO(Long id, Long userId, Long orderId) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
    }

    public HistoryOrderPO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoryOrderPO that = (HistoryOrderPO) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(orderId, that.orderId) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public String toString() {
        return "OrderHistoryPO{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, orderId, createTime, updateTime);
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
