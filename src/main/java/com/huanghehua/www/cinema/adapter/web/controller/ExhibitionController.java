package com.huanghehua.www.cinema.adapter.web.controller;

import com.huanghehua.www.cinema.app.service.ExhibitionServiceImpl;
import com.huanghehua.www.cinema.client.api.ExhibitionServiceI;
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
    private ExhibitionServiceI showFilmService;


    @Request(value = "/info",method = "get")
    public CommonResult<?> showFilmInfo(@RequestParam("name") String name,
                                        @RequestParam("maxPageSize") Integer maxPageSize,
                                        @RequestParam("currentPageNumber") Integer currentPageNumber) {

        PageAbility pageAbility = new PageAbility(maxPageSize, currentPageNumber);

        return showFilmService.show(name, pageAbility);
    }
}
