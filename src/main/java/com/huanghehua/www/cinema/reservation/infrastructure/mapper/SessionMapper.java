package com.huanghehua.www.cinema.reservation.infrastructure.mapper;

import com.huanghehua.www.cinema.reservation.infrastructure.data.SessionPO;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;
import com.huanghehua.www.orm.annotation.Param;

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
public interface SessionMapper {
    /**
     * 根据影片id获取上映场次
     *
     * @param filmId 影片id
     * @return {@link List}<{@link SessionPO}>
     */
    List<SessionPO> selectListByFilmId(@Param("filmId") Long filmId);
}
