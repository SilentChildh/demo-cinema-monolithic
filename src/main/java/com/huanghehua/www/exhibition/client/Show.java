package com.huanghehua.www.exhibition.client;

import com.huanghehua.www.common.CommonResult;

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
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> show(String name);
}
