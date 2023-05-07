package com.huanghehua.www.cinema.exhibition.client;

import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.common.PageAbility;

/**
 * 显示影片信息业务接口
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public interface Show {
    /**
     * 显示影片信息
     *
     * @param name        名字
     * @param pageAbility 页面能力
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> show(String name, PageAbility pageAbility);

    /**
     * 显示影片信息
     *
     * @param name 名字
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> show(String name);
}
