package com.huanghehua.www.cinema.domain.gateway;

import com.huanghehua.www.cinema.domain.model.FilmModel;
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
     * @return {@link List}<{@link FilmModel}>
     */
    List<FilmModel> getFilm(String name, PageAbility pageAbility);

    /**
     * 得到电影
     *
     * @param name 名字
     * @return {@link List}<{@link FilmModel}>
     */
    List<FilmModel> getFilm(String name);
}
