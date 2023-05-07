package com.huanghehua.www.exhibition.domain.gateway;

import com.huanghehua.www.exhibition.domain.FilmDomain;

import java.util.List;

/**
 * 显示信息网关
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public interface ShowGateWay {
    /**
     * 通过影片名称得到电影
     *
     * @param name 名字
     * @return {@link List}<{@link FilmDomain}>
     */
    List<FilmDomain> getFilm(String name);
}
