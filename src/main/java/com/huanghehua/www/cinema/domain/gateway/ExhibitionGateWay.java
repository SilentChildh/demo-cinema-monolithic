package com.huanghehua.www.cinema.domain.gateway;

import com.huanghehua.www.cinema.domain.exhibition.FilmModel;
import com.huanghehua.www.common.PageAbility;

import java.util.List;

/**
 * 显示信息网关
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/11
 */
public interface ExhibitionGateWay {
    /**
     * 分页形式
     * 通过影片名称得到电影
     *
     * @param name        名字
     * @param pageAbility 页面能力
     * @return {@link List}<{@link FilmModel}>
     */
    List<FilmModel> listPageFilm(String name, PageAbility pageAbility);

    /**
     * 得到电影
     *
     * @param name 名字
     * @return {@link List}<{@link FilmModel}>
     */
    List<FilmModel> listFilm(String name);


    /**
     * 通过电影id得到电影模型
     *
     * @param id id
     * @return {@link FilmModel}
     */
    FilmModel getFilm(Long id);
}
