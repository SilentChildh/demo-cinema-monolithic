package com.huanghehua.www.cinema.exhibition.infrastructure.mapper;

import com.huanghehua.www.cinema.exhibition.infrastructure.data.FilmPO;
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
    List<FilmPO> selectListByNameWithPage(@Param("name") String name,
                                          @Param("fromIndex") Integer fromIndex,
                                          @Param("currentPageSize") Integer currentPageSize);

    /**
     * 根据影片名称获取影片信息
     *
     * @param name 名字
     * @return {@link List}<{@link FilmPO}>
     */
    List<FilmPO> selectListByName(@Param("name") String name);
    /**
     * 根据影片名称获取该影片的数量
     *
     * @param name 名字
     * @return {@link Long}
     */
    Long selectCountByName(@Param("name") String name);
}
