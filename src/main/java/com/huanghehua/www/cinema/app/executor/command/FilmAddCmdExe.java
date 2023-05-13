package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.app.convertor.FilmConvertor;
import com.huanghehua.www.cinema.client.dto.command.FilmAddCmd;
import com.huanghehua.www.cinema.infrastructure.data.FilmPO;
import com.huanghehua.www.cinema.infrastructure.mapper.FilmMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 电影添加cmd 的executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
@Bean
@Interceptable
public class FilmAddCmdExe {
    @Reference
    private FilmMapper filmMapper;

    public CommonResult<?> execute(FilmAddCmd filmAddCmd) {
        // 转换为持久化对象
        FilmPO filmPo = FilmConvertor.dtoToPo(filmAddCmd);

        // 执行添加影片信息
        int success = filmMapper.insertFilm(filmPo);
        return success > 0 ? CommonResult.operateSuccess() : CommonResult.operateFail("添加影片信息失败");
    }
}
