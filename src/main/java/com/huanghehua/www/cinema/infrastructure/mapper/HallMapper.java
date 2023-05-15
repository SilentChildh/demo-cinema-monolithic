package com.huanghehua.www.cinema.infrastructure.mapper;

import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;
import com.huanghehua.www.orm.annotation.Param;

/**
 * 影厅映射器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/15
 */
@Mapper
@Bean
public interface HallMapper {

    /**
     * 添加影厅
     *
     * @param capacity 能力
     * @return int
     */
    int insertHall(@Param("capacity") Integer capacity);

    /**
     * 通过id删除影厅
     *
     * @param id id
     * @return int
     */
    int deleteHallById(@Param("id") Long id);

    /**
     * 通过id获取容量
     *
     * @param id id
     * @return {@link Integer}
     */
    Integer getCapacityById(@Param("id") Long id);
}
