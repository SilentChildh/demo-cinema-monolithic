package com.huanghehua.www.cinema.client.dto.query;

import java.util.Objects;

/**
 * 查询订单历史记录表的qry类型 dto模型
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class HistoryOrderDetailListQry {
    /**
     * 用户id
     */
    private Long userId;

    public HistoryOrderDetailListQry(Long userId) {
        this.userId = userId;
    }

    public HistoryOrderDetailListQry() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoryOrderDetailListQry that = (HistoryOrderDetailListQry) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "OrderHistoryListQry{" +
                "userId=" + userId +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
