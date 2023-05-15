package com.huanghehua.www.cinema.app.convertor;

import com.huanghehua.www.cinema.client.dto.SeatDTO;
import com.huanghehua.www.cinema.client.dto.command.SeatAddCmd;
import com.huanghehua.www.cinema.infrastructure.data.SeatPO;

import java.util.ArrayList;
import java.util.List;

/**
 * 场次数据模型 转换器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class SeatConvertor {
    /**
     * 数据库持久化数据模型转化为dto模型
     *
     * @param seatPo 座位持久化对象
     * @return {@link SeatDTO}
     */
    public static SeatDTO poToDto(SeatPO seatPo) {
        Long id = seatPo.getId();
        Long hallId = seatPo.getHallId();
        Integer row = seatPo.getRow();
        Integer column = seatPo.getColumn();
        String status = seatPo.getStatus() ? SeatDTO.TRUE_STATUS : SeatDTO.FALSE_STATUS;

        return new SeatDTO(id, hallId, row, column, status);
    }

    /**
     * 数据库持久化数据模型集转化为dto模型集
     *
     * @param seatPoList 座位订单列表
     * @return {@link List}<{@link SeatDTO}>
     */
    public static List<SeatDTO> poListToDto(List<SeatPO> seatPoList) {
        ArrayList<SeatDTO> list = new ArrayList<>();

        for (SeatPO seatPo : seatPoList) {
            // 利用转换器进行类型转换
            SeatDTO seatDTO = SeatConvertor.poToDto(seatPo);

            list.add(seatDTO);
        }
        return list;
    }


    /**
     * SeatAddCmd类型的dto模型转化为数据库持久化数据模型
     *
     * @param seatAddCmd 座位添加cmd
     * @return {@link SeatPO}
     */
    public static SeatPO dtoToPo(SeatAddCmd seatAddCmd) {
        return new SeatPO(
                seatAddCmd.getHallId(),
                seatAddCmd.getRow(),
                seatAddCmd.getColumn());
    }
}
