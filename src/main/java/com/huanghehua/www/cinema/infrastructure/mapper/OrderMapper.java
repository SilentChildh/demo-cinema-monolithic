package com.huanghehua.www.cinema.infrastructure.mapper;

import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;

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
     * @return int
     */
    int insertOrder();

}
