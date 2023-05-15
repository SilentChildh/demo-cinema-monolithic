package com.huanghehua.www.cinema.client.api;

import com.huanghehua.www.cinema.client.dto.FilmDTO;
import com.huanghehua.www.cinema.client.dto.command.*;
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

    /**
     * 添加电影场次安排
     *
     * @param scheduleAddCmd 计划添加cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> addSchedule(ScheduleAddCmd scheduleAddCmd);

    /**
     * 删除电影场次
     *
     * @param scheduleRemoveCmd 安排删除cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> removeSchedule(ScheduleRemoveCmd scheduleRemoveCmd);

    /**
     * 添加影厅座位安排
     *
     * @param seatAddCmd 座位添加cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> addSeat(SeatAddCmd seatAddCmd);


    /**
     * 移除座位
     *
     * @param seatRemoveCmd 座位移除cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> removeSeat(SeatRemoveCmd seatRemoveCmd);

    /**
     * 添加影厅
     *
     * @param capacity 能力
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> addHall(Integer capacity);

    /**
     * 移除影厅
     *
     * @param hallId 大厅id
     * @return {@link CommonResult}<{@link ?}>
     */
    CommonResult<?> removeHall(Long hallId);
}
