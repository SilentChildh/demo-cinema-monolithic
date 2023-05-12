package com.huanghehua.www.cinema.infrastructure.mapper;

import com.huanghehua.www.cinema.infrastructure.data.FilmPO;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;
import com.huanghehua.www.orm.annotation.Param;

import java.util.List;

/**
 * 电影映射器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
@Mapper
public interface FilmMapper {
    /**
     * 根据影片名称获取影片信息
     *
     * @param name            名字
     * @param fromIndex       从指数
     * @param currentPageSize 当前页面大小
     * @return {@link List}<{@link FilmPO}>
     */
    List<FilmPO> listPageByName(@Param("name") String name,
                                @Param("fromIndex") Integer fromIndex,
                                @Param("currentPageSize") Integer currentPageSize);

    /**
     * 根据影片名称获取影片信息
     *
     * @param name 名字
     * @return {@link List}<{@link FilmPO}>
     */
    List<FilmPO> listByName(@Param("name") String name);
    /**
     * 根据影片名称获取该影片的数量
     *
     * @param name 名字
     * @return {@link Long}
     */
    Long countAllByName(@Param("name") String name);

    /**
     * 通过id获取电影信息
     *
     * @param id id
     * @return {@link FilmPO}
     */
    FilmPO getFilmById(@Param("id") Long id);
}
