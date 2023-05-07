package com.huanghehua.www.exhibition.infrastructure.gatewayimpl;

import com.huanghehua.www.exhibition.domain.FilmDomain;
import com.huanghehua.www.exhibition.domain.gateway.ShowGateWay;
import com.huanghehua.www.exhibition.infrastructure.data.FilmPO;
import com.huanghehua.www.exhibition.infrastructure.mapper.FilmMapper;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;

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
    public List<FilmDomain> getFilm(String name) {
        ArrayList<FilmDomain> filmDomains = new ArrayList<>(12);
        List<FilmPO> films = filmMapper.selectListByName(name);
        for (FilmPO film : films) {
            String filmName = film.getName();
            String director = film.getDirector();
            String actor = film.getActor();
            LocalDateTime releaseTime = film.getReleaseTime();
            LocalTime duration = film.getDuration();
            String poster = film.getPoster();

            FilmDomain filmDomain = new FilmDomain(filmName, director, actor, releaseTime,
                    duration, poster);

            filmDomains.add(filmDomain);
        }

        return filmDomains;
    }
}
