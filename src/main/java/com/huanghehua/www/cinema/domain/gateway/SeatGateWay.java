package com.huanghehua.www.cinema.domain.gateway;

import com.huanghehua.www.cinema.domain.seat.SeatModel;

/**
 * 座位入口
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/17
 */
public interface SeatGateWay {
    /**
     * 增加座位
     *
     * @param seatModel 座位模型
     * @return boolean
     */
    boolean addSeat(SeatModel seatModel);

    /**
     * 获得席位模型
     *
     * @param hallId 大厅id
     * @param row    行
     * @param column 列
     * @return {@link SeatModel}
     */
    SeatModel getSeatModel(Long hallId, Integer row, Integer column);
}
