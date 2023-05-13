package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.app.service.ExhibitionServiceImpl;
import com.huanghehua.www.cinema.client.api.ExhibitionServiceI;
import com.huanghehua.www.cinema.client.dto.command.FilmAddCmd;
import com.huanghehua.www.cinema.client.dto.command.FilmRemoveCmd;
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

    @Request(value = "/add", method = "post")
    public CommonResult<?> addFilm(FilmAddCmd filmAddCmd) {
        return exhibitionService.addFilm(filmAddCmd);
    }

    @Request(value = "/remove", method = "post")
    public CommonResult<?> removeFilm(FilmRemoveCmd filmRemoveCmd) {
        return exhibitionService.removeFilm(filmRemoveCmd);
    }
}
