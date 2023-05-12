package com.huanghehua.www.cinema.infrastructure.mapper;

import com.huanghehua.www.cinema.infrastructure.data.SchedulePO;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;
import com.huanghehua.www.orm.annotation.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 场次映射器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Mapper
@Bean
public interface ScheduleMapper {
    /**
     * 根据影片id获取上映场次
     *
     * @param filmId 影片id
     * @return {@link List}<{@link SchedulePO}>
     */
    List<SchedulePO> listByFilmId(@Param("filmId") Long filmId);

    /**
     * 通过id获取价格
     *
     * @param id id
     * @return {@link BigDecimal}
     */
    BigDecimal getPriceById(@Param("id") Long id);

    /**
     * 通过id获取场次信息
     *
     * @param id id
     * @return {@link SchedulePO}
     */
    SchedulePO getScheduleById(@Param("id") Long id);
}
