package com.huanghehua.www.cinema.app.service;

import com.huanghehua.www.cinema.app.convertor.FilmConvertor;
import com.huanghehua.www.cinema.app.executor.command.*;
import com.huanghehua.www.cinema.client.dto.FilmDTO;
import com.huanghehua.www.cinema.client.dto.command.*;
import com.huanghehua.www.cinema.domain.gateway.ExhibitionGateWay;
import com.huanghehua.www.cinema.infrastructure.gatewayimpl.ExhibitionGateWayImpl;
import com.huanghehua.www.cinema.infrastructure.mapper.HallMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.dispatch.handler.ParametersVerifyHandler;
import com.huanghehua.www.dispatch.model.VerifyServiceMethodParam;
import com.huanghehua.www.cinema.client.api.ExhibitionServiceI;
import com.huanghehua.www.cinema.domain.exhibition.FilmModel;
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
public class ExhibitionServiceImpl implements ExhibitionServiceI {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    /**
     * TODO 注解上的value是infrastructure模块中的ExhibitionGateWayImpl，那么算不算是依赖infrastructure模块了呢
     */
    @Reference(ExhibitionGateWayImpl.class)
    private ExhibitionGateWay exhibitionGateWay;
    @Reference
    private FilmAddCmdExe filmAddCmdExe;
    @Reference
    private FilmRemoveCmdExe filmRemoveCmdExe;
    @Reference
    private ScheduleAddCmdExe scheduleAddCmdExe;
    @Reference
    private ScheduleRemoveCmdExe scheduleRemoveCmdExe;
    @Reference
    private SeatAddCmdExe seatAddCmdExe;
    @Reference
    private SeatRemoveCmdExe seatRemoveCmdExe;
    @Reference
    private HallMapper hallMapper;
    @Reference
    private FilmUndoRemoveCmdExe filmUndoRemoveCmdExe;

    @Override
    public CommonResult<List<FilmDTO>> showListPageInfo(String name, PageAbility pageAbility) {
        LOGGER.log(Level.INFO, "{0} invoke show() method", Thread.currentThread());
        // 验证参数
        VerifyServiceMethodParam verifyServiceMethodParam = new VerifyServiceMethodParam(ExhibitionServiceImpl.class,
                "showListPageInfo",
                new Class<?>[]{String.class, PageAbility.class}, new Object[]{name, pageAbility});
        ParametersVerifyHandler.handle(verifyServiceMethodParam);

        // 执行业务
        List<FilmModel> filmModels = exhibitionGateWay.listPageFilm(name, pageAbility);

        // 如果未上映，则从list中删除
        filmModels.removeIf(filmModel -> !filmModel.getStatus());

        // 数据结构转换
        List<FilmDTO> list = FilmConvertor.modelListToDto(filmModels);
        return CommonResult.operateSuccess(list);
    }

    @Override
    public CommonResult<List<FilmDTO>> showListInfo(String name) {
        // 验证参数
        VerifyServiceMethodParam verifyServiceMethodParam = new VerifyServiceMethodParam(ExhibitionServiceImpl.class,
                "show",
                new Class<?>[]{String.class}, new Object[]{name});
        ParametersVerifyHandler.handle(verifyServiceMethodParam);



        // 执行业务
        List<FilmModel> filmModels = exhibitionGateWay.listFilm(name);

        // 如果未上映，则从list中删除
        filmModels.removeIf(filmModel -> !filmModel.getStatus());

        // 数据结构转换
        List<FilmDTO> list = FilmConvertor.modelListToDto(filmModels);
        return CommonResult.operateSuccess(list);
    }

    @Override
    public CommonResult<FilmDTO> getFilmInfo(Long filmId) {
        FilmModel film = exhibitionGateWay.getFilm(filmId);
        FilmDTO filmDTO = FilmConvertor.modelToDto(film);

        return CommonResult.operateSuccess(filmDTO);
    }

    @Override
    public CommonResult<?> addFilm(FilmAddCmd filmAddCmd) {
        return filmAddCmdExe.execute(filmAddCmd);
    }

    @Override
    public CommonResult<?> removeFilm(FilmRemoveCmd filmRemoveCmd) {
        return filmRemoveCmdExe.execute(filmRemoveCmd);
    }

    @Override
    public CommonResult<?> undoRemoveFilm(FilmUndoRemoveCmd filmUndoRemoveCmd) {
        return filmUndoRemoveCmdExe.execute(filmUndoRemoveCmd);
    }

    @Override
    public CommonResult<?> addSchedule(ScheduleAddCmd scheduleAddCmd) {
        return scheduleAddCmdExe.execute(scheduleAddCmd);
    }

    @Override
    public CommonResult<?> removeSchedule(ScheduleRemoveCmd scheduleRemoveCmd) {
        return scheduleRemoveCmdExe.execute(scheduleRemoveCmd);
    }

    @Override
    public CommonResult<?> addSeat(SeatAddCmd seatAddCmd) {
        return seatAddCmdExe.execute(seatAddCmd);
    }

    @Override
    public CommonResult<?> removeSeat(SeatRemoveCmd seatRemoveCmd) {
        return seatRemoveCmdExe.execute(seatRemoveCmd);
    }

    @Override
    public CommonResult<?> addHall(Integer capacity) {
        int success = hallMapper.insertHall(capacity);
        return success > 0 ? CommonResult.operateSuccess() : CommonResult.operateFail("添加影厅失败");
    }

    @Override
    public CommonResult<?> removeHall(Long hallId) {
        int success = hallMapper.deleteHallById(hallId);
        return success > 0 ? CommonResult.operateSuccess() : CommonResult.operateFail("移除影厅失败");
    }
}
