package com.huanghehua.www.cinema.exhibition.web;

import com.huanghehua.www.cinema.exhibition.app.ShowExecutor;
import com.huanghehua.www.cinema.exhibition.client.Show;
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
public class FilmController {

    @Reference(ShowExecutor.class)
    private Show showExecutor;


    @Request(value = "/info",method = "get")
    public CommonResult<?> showFilmInfo(@RequestParam("name") String name,
                                        @RequestParam("maxPageSize") Integer maxPageSize,
                                        @RequestParam("currentPageNumber") Integer currentPageNumber) {

        PageAbility pageAbility = new PageAbility(maxPageSize, currentPageNumber);

        return showExecutor.show(name, pageAbility);
    }
}