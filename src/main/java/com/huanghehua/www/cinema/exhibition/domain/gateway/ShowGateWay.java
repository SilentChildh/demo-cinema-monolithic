package com.huanghehua.www.cinema.exhibition.domain.gateway;

import com.huanghehua.www.cinema.exhibition.domain.FilmDomain;
import com.huanghehua.www.common.PageAbility;

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
     * 分页形式
     * 通过影片名称得到电影
     *
     * @param name        名字
     * @param pageAbility 页面能力
     * @return {@link List}<{@link FilmDomain}>
     */
    List<FilmDomain> getFilm(String name, PageAbility pageAbility);

    /**
     * 得到电影
     *
     * @param name 名字
     * @return {@link List}<{@link FilmDomain}>
     */
    List<FilmDomain> getFilm(String name);
}
