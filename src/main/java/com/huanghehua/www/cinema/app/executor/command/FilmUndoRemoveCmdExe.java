package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.app.executor.Executor;
import com.huanghehua.www.cinema.client.dto.command.FilmUndoRemoveCmd;
import com.huanghehua.www.cinema.infrastructure.mapper.FilmMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 电影撤消删除cmd exe
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/15
 */
@Bean
@Interceptable
public class FilmUndoRemoveCmdExe implements Executor<FilmUndoRemoveCmd> {

    @Reference
    private FilmMapper filmMapper;

    @Override
    public CommonResult<?> execute(FilmUndoRemoveCmd filmUndoRemoveCmd) {
        Long filmId = filmUndoRemoveCmd.getFilmId();
        int success = filmMapper.updateStatusById(filmId, 1);
        return success > 0 ? CommonResult.operateSuccess() : CommonResult.operateFail("恢复上映失败");
    }
}
