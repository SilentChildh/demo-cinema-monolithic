package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.app.service.ExhibitionServiceImpl;
import com.huanghehua.www.cinema.client.api.ExhibitionServiceI;
import com.huanghehua.www.cinema.client.dto.command.*;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.annotation.Request;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

/**
 * 管理控制器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
@Bean
@Request("/admin")
public class AdminController {
    @Reference(ExhibitionServiceImpl.class)
    private ExhibitionServiceI exhibitionService;

    @Request(value = "/add-film", method = "post")
    public CommonResult<?> addFilm(FilmAddCmd filmAddCmd) {
        return exhibitionService.addFilm(filmAddCmd);
    }

    @Request(value = "/remove-film", method = "post")
    public CommonResult<?> removeFilm(FilmRemoveCmd filmRemoveCmd) {
        return exhibitionService.removeFilm(filmRemoveCmd);
    }

    @Request(value = "/add-schedule", method = "post")
    public CommonResult<?> addSchedule(ScheduleAddCmd scheduleAddCmd) {
        return exhibitionService.addSchedule(scheduleAddCmd);
    }

    @Request(value = "/remove-Schedule", method = "post")
    public CommonResult<?> removeSchedule(ScheduleRemoveCmd scheduleRemoveCmd) {
        return exhibitionService.removeSchedule(scheduleRemoveCmd);
    }

    @Request(value = "/add-seat", method = "post")
    public CommonResult<?> addSeat(SeatAddCmd seatAddCmd) {
        return exhibitionService.addSeat(seatAddCmd);
    }

    public CommonResult<?> showOrderInfo() {

    }

}