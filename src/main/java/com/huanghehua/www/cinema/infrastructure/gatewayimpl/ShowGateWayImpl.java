package com.huanghehua.www.cinema.infrastructure.gatewayimpl;

import com.huanghehua.www.cinema.domain.gateway.ShowGateWay;
import com.huanghehua.www.cinema.infrastructure.data.FilmPO;
import com.huanghehua.www.cinema.domain.model.FilmModel;
import com.huanghehua.www.cinema.infrastructure.mapper.FilmMapper;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.common.PageAbility;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 显示domain实现类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
public class ShowGateWayImpl implements ShowGateWay {
    @Reference
    private FilmMapper filmMapper;
    @Override
    public List<FilmModel> getFilm(String name, PageAbility pageAbility) {
        // 先设置记录总数（以便完成逻辑分页），再获取开始索引值和当前页面大小
        Long count = filmMapper.selectCountByName(name);
        pageAbility.setRecordCount(Math.toIntExact(count));
        Integer fromIndex = pageAbility.getFromIndex();
        Integer currentPageSize = pageAbility.getCurrentPageSize();


        ArrayList<FilmModel> filmModels = new ArrayList<>(12);
        List<FilmPO> films = filmMapper.selectListByNameWithPage(name, fromIndex, currentPageSize);
        for (FilmPO film : films) {
            String filmName = film.getName();
            String director = film.getDirector();
            String actor = film.getActor();
            LocalDateTime releaseTime = film.getReleaseTime();
            LocalTime duration = film.getDuration();
            String poster = film.getPoster();

            FilmModel filmModel = new FilmModel(filmName, director, actor, releaseTime,
                    duration, poster);

            filmModels.add(filmModel);
        }

        return filmModels;
    }

    @Override
    public List<FilmModel> getFilm(String name) {
        ArrayList<FilmModel> filmModels = new ArrayList<>(12);
        List<FilmPO> films = filmMapper.selectListByName(name);

        for (FilmPO film : films) {
            String filmName = film.getName();
            String director = film.getDirector();
            String actor = film.getActor();
            LocalDateTime releaseTime = film.getReleaseTime();
            LocalTime duration = film.getDuration();
            String poster = film.getPoster();

            FilmModel filmModel = new FilmModel(filmName, director, actor, releaseTime,
                    duration, poster);

            filmModels.add(filmModel);
        }

        return filmModels;
    }
}
