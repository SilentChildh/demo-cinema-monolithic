package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.app.service.ExhibitionServiceImpl;
import com.huanghehua.www.cinema.app.service.OrderServiceImpl;
import com.huanghehua.www.cinema.client.api.ExhibitionServiceI;
import com.huanghehua.www.cinema.client.api.OrderServiceI;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.dispatch.annotation.RequestParam;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.common.PageAbility;

/**
 * 电影控制器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
@Request("/exhibition")
public class ExhibitionController {

    @Reference(ExhibitionServiceImpl.class)
    private ExhibitionServiceI exhibitionService;

    @Reference(OrderServiceImpl.class)
    private OrderServiceI orderService;


    /**
     * 显示影片信息
     *
     * @param name              名字
     * @param maxPageSize       最大页面大小
     * @param currentPageNumber 当前页码
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/info", method = "get")
    public CommonResult<?> showFilmInfo(@RequestParam("name") String name,
                                        @RequestParam("maxPageSize") Integer maxPageSize,
                                        @RequestParam("currentPageNumber") Integer currentPageNumber) {

        PageAbility pageAbility = new PageAbility(maxPageSize, currentPageNumber);

        return exhibitionService.showListPageInfo(name, pageAbility);
    }

    /**
     * 根据电影id，展示电影场次表
     *
     * @param filmId 影片id
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/schedule", method = "get")
    public CommonResult<?> showFilmSchedule(@RequestParam("filmId") Long filmId) {
        return orderService.showSchedule(filmId);
    }

    // TODO 还需要添加分页查询场次表

    /**
     * 根据场次id，展示可用座位表
     *
     * @param scheduleId 场次id
     * @return {@link CommonResult}<{@link ?}>
     */
    @Request(value = "/seat", method = "get")
    public CommonResult<?> showActiveSeat(@RequestParam("scheduleId") Long scheduleId) {
        return orderService.showActiveSeat(scheduleId);
    }

    // TODO 还需要添加分页查询座位表

}
