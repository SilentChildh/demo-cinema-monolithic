package com.huanghehua.www.cinema.client.api;

import com.huanghehua.www.cinema.client.dto.FilmDTO;
import com.huanghehua.www.cinema.client.dto.command.FilmAddCmd;
import com.huanghehua.www.cinema.client.dto.command.FilmRemoveCmd;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.common.PageAbility;

import java.util.List;

/**
 * 显示影片信息业务接口
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public interface ExhibitionServiceI {
    /**
     * 显示影片信息
     *
     * @param name        名字
     * @param pageAbility 页面能力
     * @return {@link CommonResult}<{@link List}<{@link FilmDTO}>>
     */
    CommonResult<List<FilmDTO>> showListPageInfo(String name, PageAbility pageAbility);

    /**
     * 显示影片信息
     *
     * @param name 名字
     * @return {@link CommonResult}<{@link List}<{@link FilmDTO}>>
     */
    CommonResult<List<FilmDTO>> showListInfo(String name);

    /**
     * 根据电影id获取电影信息
     *
     * @param filmId 影片id
     * @return {@link CommonResult}<{@link FilmDTO}>
     */
    CommonResult<FilmDTO> getFilmInfo(Long filmId);


    /**
     * 添加电影信息
     *
     * @param filmAddCmd 电影添加cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> addFilm(FilmAddCmd filmAddCmd);

/*    *//**
     * 更新电影
     *
     * @return {@link CommonResult}<{@link ?}>
     *//*
    CommonResult<?> updateFilm();*/

    /**
     * 删除电影
     *
     * @param filmRemoveCmd 电影删除cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> removeFilm(FilmRemoveCmd filmRemoveCmd);
}
