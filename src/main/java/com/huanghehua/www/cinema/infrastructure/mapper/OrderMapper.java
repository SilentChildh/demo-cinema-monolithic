package com.huanghehua.www.cinema.infrastructure.mapper;

import com.huanghehua.www.cinema.infrastructure.data.OrderPO;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;
import com.huanghehua.www.orm.annotation.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
     * 根据id更新订单状态, 1为有效，0为无效
     *
     * @param status 状态
     * @param id     id
     * @return int
     */
    int updateOrderStatusById(@Param("id") Long id,
                              @Param("status") int status);


    /**
     * 通过用户id获取订单id列表
     *
     * @param userId 用户id
     * @return {@link List}<{@link Long}>
     */
    List<Long> listOrderIdByUserId(@Param("userId") Long userId);

    /**
     * 通过订单id获取历史记录更新时间
     *
     * @param id id
     * @return {@link LocalDateTime}
     */
    LocalDateTime getUpdateTimeById(@Param("id") Long id);

    /**
     * 通过订单id获取订单创建时间
     *
     * @param id id
     * @return {@link LocalDateTime}
     */
    LocalDateTime getCreateTimeById(@Param("id") Long id);

    /**
     * 通过id获取订单状态
     *
     * @param id id
     * @return {@link Boolean}
     */
    Boolean getStatusById(@Param("id") Long id);
}
