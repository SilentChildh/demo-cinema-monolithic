package com.huanghehua.www.exhibition.infrastructure.mapper;

import com.huanghehua.www.exhibition.infrastructure.data.FilmPO;
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
     * @param name 名字
     * @return {@link List}<{@link FilmPO}>
     */
    List<FilmPO> selectListByName(@Param("name") String name);
}
