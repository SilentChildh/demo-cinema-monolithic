package com.huanghehua.www.cinema.infrastructure.mapper;

import com.huanghehua.www.cinema.infrastructure.data.OrderPO;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;
import com.huanghehua.www.orm.annotation.Param;

import java.math.BigDecimal;

/**
 * 顺序映射器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Mapper
@Bean
public interface OrderMapper {
    /**
     * 插入订单
     *
     * @param userId     用户id
     * @param scheduleId 安排id
     * @param price      价格
     * @param seatId     座位id
     * @return int
     */
    int insertOrder(@Param("userId") Long userId,
                    @Param("scheduleId") Long scheduleId,
                    @Param("seatId") Long seatId,
                    @Param("price")BigDecimal price);

    /**
     * 通过id获取订单信息
     *
     * @param id id
     * @return {@link OrderPO}
     */
    OrderPO getOrderById(@Param("id") Long id);

    /**
     * 更新订单状态, 1为有效，0为无效
     *
     * @param status 状态
     * @return int
     */
    int updateOrderStatus(@Param("status") int status);
}
