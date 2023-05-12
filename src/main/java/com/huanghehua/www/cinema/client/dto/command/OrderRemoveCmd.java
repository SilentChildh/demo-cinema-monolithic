package com.huanghehua.www.cinema.client.dto.command;

import java.util.Objects;

/**
 * 订单删除command模型
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class OrderRemoveCmd {
    /**
     * 订单id
     */
    private Long orderId;

    public OrderRemoveCmd(Long orderId) {
        this.orderId = orderId;
    }

    public OrderRemoveCmd() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderRemoveCmd that = (OrderRemoveCmd) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "OrderRemoveCmd{" +
                "orderId=" + orderId +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
