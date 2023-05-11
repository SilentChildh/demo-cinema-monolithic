package com.huanghehua.www.cinema.domain.order.model;

import java.util.List;

/**
 * 座位域
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class SeatModel {
    /**
     * 座位号
     */
    private Long id;
    /**
     * 影片id
     */
    private Long filmId;
    /**
     * 座位状态
     */
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
