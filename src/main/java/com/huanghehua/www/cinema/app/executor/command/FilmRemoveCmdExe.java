package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.client.dto.command.FilmRemoveCmd;
import com.huanghehua.www.cinema.infrastructure.mapper.FilmMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 电影删除cmd 的executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
@Bean
@Interceptable
public class FilmRemoveCmdExe {
    @Reference
    private FilmMapper filmMapper;

    /**
     * 执行下映操作，即将film表中影片的status改为0
     *
     * @param filmRemoveCmd 电影删除cmd
     * @return {@link CommonResult}<{@link ?}>
     */
    public CommonResult<?> execute(FilmRemoveCmd filmRemoveCmd) {
        // 获取影片id
        Long filmId = filmRemoveCmd.getFilmId();

        // 执行下映操作
        int success = filmMapper.updateStatusById(filmId, 0);
        return success > 0 ? CommonResult.operateSuccess() : CommonResult.operateFail("下映操作失败，请重新尝试...");
    }
}
