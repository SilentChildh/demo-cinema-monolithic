package com.huanghehua.www.cinema.infrastructure.mapper;

import com.huanghehua.www.cinema.infrastructure.data.SeatPO;
import com.huanghehua.www.orm.annotation.Param;

import java.util.List;

/**
 * 座位映射器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public interface SeatMapper {

    /**
     * 根据影厅id获取座位信息表
     *
     * @param hallId 大厅id
     * @return {@link SeatPO}
     */
    List<SeatPO> listActiveSeatByHallId(@Param("hallId") Long hallId);

    /**
     * 根据座位id更新座位状态, 1为占用，0为空闲
     *
     * @param status 状态
     * @param id     座位id
     * @return int
     */
    int updateSeatStatusById(@Param("id") Long id,
                             @Param("status") int status);
}
