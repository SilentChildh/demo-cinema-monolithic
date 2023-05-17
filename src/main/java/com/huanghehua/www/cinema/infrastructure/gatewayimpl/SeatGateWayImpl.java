package com.huanghehua.www.cinema.infrastructure.gatewayimpl;

import com.huanghehua.www.cinema.domain.gateway.SeatGateWay;
import com.huanghehua.www.cinema.domain.seat.SeatModel;
import com.huanghehua.www.cinema.infrastructure.mapper.HallMapper;
import com.huanghehua.www.cinema.infrastructure.mapper.SeatMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Reference;

/**
 * 座位入口impl
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/17
 */
public class SeatGateWayImpl implements SeatGateWay {
    @Reference
    private SeatMapper seatMapper;
    @Reference
    private HallMapper hallMapper;
    @Override
    public boolean addSeat(SeatModel seatModel) {
        return false;
    }


    @Override
    public SeatModel getSeatModel(Long hallId, Integer row, Integer column) {
        SeatModel seatModel = new SeatModel(hallId, row, column);

        // 设置是否存在该座位
        Long countSeat = seatMapper.countSeat(hallId, row, column);
        seatModel.setExistence(countSeat != 0);

        // 设置现存的座位和影厅的最大容量
        Integer capacity = hallMapper.getCapacityById(hallId);
        Long seats = seatMapper.countSeatByHallId(hallId);
        seatModel.setCapacity(capacity);
        seatModel.setPresentSeats(seats);


        return seatModel;
    }
}
