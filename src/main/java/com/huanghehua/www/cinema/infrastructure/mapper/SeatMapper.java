package com.huanghehua.www.cinema.infrastructure.mapper;

import com.huanghehua.www.cinema.infrastructure.data.SeatPO;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;
import com.huanghehua.www.orm.annotation.Param;

import java.util.List;

/**
 * 座位映射器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
@Bean
@Mapper
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

    /**
     * 通过id获取座位状态, 1为占用，0为空闲
     *
     * @param id id
     * @return int
     */
    Boolean getStatusById(@Param("id") Long id);


    /**
     * 通过id获取座位信息
     *
     * @param id id
     * @return {@link SeatPO}
     */
    SeatPO getSeatById(@Param("id") Long id);

    /**
     * 在影厅中添加座位
     *
     * @param seatPo 座位阿宝
     * @return int
     */
    int insertSeat(SeatPO seatPo);

    /**
     * 通过id删除座位
     *
     * @param id id
     * @return int
     */
    int deleteSeatById(@Param("id") Long id);

    /**
     * 通过影厅id、行号、列号获得座位数量
     *
     * @param hallId 大厅id
     * @param row    行
     * @param column 列
     * @return {@link Long}
     */
    Long countSeat(@Param("hallId") Long hallId,
                      @Param("row") Integer row,
                      @Param("column") Integer column);

    /**
     * 通过影厅id、行号、列号获得座位数量
     *
     * @param hallId 大厅id
     * @return {@link Long}
     */
    Long countSeatByHallId(@Param("hallId") Long hallId);
}
