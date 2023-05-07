package com.huanghehua.www.exhibition.app;

import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.handler.ParametersVerifyHandler;
import com.huanghehua.www.dispatch.model.VerifyServiceMethodParam;
import com.huanghehua.www.exhibition.client.Show;
import com.huanghehua.www.exhibition.domain.FilmDomain;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

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
public class ShowExecutor implements Show {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Reference
    private FilmDomain filmDomain;

    @Override
    public CommonResult<?> show(String name) {
        LOGGER.log(Level.INFO, "{0} invoke show() method", Thread.currentThread());
        // 验证参数
        VerifyServiceMethodParam verifyServiceMethodParam = new VerifyServiceMethodParam(ShowExecutor.class,
                "show",
                new Class<?>[]{String.class}, new Object[]{name});
        ParametersVerifyHandler.handle(verifyServiceMethodParam);

        // 执行业务
        List<FilmDomain> film = filmDomain.getFilm(name);
        return CommonResult.operateSuccess(film);
    }
}
