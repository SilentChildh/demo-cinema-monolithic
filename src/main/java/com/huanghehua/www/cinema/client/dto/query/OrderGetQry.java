package com.huanghehua.www.cinema.client.dto.query;

import java.util.Objects;

/**
 * 查询单个订单的query类型dto
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class OrderGetQry {
    /**
     * 订单id
     */
    private Long id;

    public OrderGetQry(Long id) {
        this.id = id;
    }

    public OrderGetQry() {
    }

    @Override
    public String toString() {
        return "OrderGetQry{" +
                "id=" + id +
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
        OrderGetQry that = (OrderGetQry) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
