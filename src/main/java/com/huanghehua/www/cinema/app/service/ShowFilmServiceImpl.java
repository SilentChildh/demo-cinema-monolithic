package com.huanghehua.www.cinema.app.service;

import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.handler.ParametersVerifyHandler;
import com.huanghehua.www.dispatch.model.VerifyServiceMethodParam;
import com.huanghehua.www.cinema.client.api.ShowFilmServiceI;
import com.huanghehua.www.cinema.domain.model.FilmModel;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;
import com.huanghehua.www.common.PageAbility;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 显示执行器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
@Interceptable
public class ShowFilmServiceImpl implements ShowFilmServiceI {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Reference
    private FilmModel filmModel;

    @Override
    public CommonResult<?> show(String name, PageAbility pageAbility) {
        LOGGER.log(Level.INFO, "{0} invoke show() method", Thread.currentThread());
        // 验证参数
        VerifyServiceMethodParam verifyServiceMethodParam = new VerifyServiceMethodParam(ShowFilmServiceImpl.class,
                "show",
                new Class<?>[]{String.class, PageAbility.class}, new Object[]{name, pageAbility});
        ParametersVerifyHandler.handle(verifyServiceMethodParam);


        // 执行业务
        List<FilmModel> film = filmModel.getFilm(name, pageAbility);
        return CommonResult.operateSuccess(film);
    }

    @Override
    public CommonResult<?> show(String name) {
        // 验证参数
        VerifyServiceMethodParam verifyServiceMethodParam = new VerifyServiceMethodParam(ShowFilmServiceImpl.class,
                "show",
                new Class<?>[]{String.class}, new Object[]{name});
        ParametersVerifyHandler.handle(verifyServiceMethodParam);


        // 执行业务
        List<FilmModel> film = filmModel.getFilm(name);
        return CommonResult.operateSuccess(film);
    }
}
