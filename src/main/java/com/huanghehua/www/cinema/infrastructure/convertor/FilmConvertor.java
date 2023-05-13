package com.huanghehua.www.cinema.infrastructure.convertor;

import com.huanghehua.www.cinema.domain.exhibition.FilmModel;
import com.huanghehua.www.cinema.infrastructure.data.FilmPO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 电影 数据模型 转换器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class FilmConvertor {
    /**
     * 数据库持久化对象转化为领域中的模型
     *
     * @param filmPo 电影阿宝
     * @return {@link FilmModel}
     */
    public static FilmModel poToModel(FilmPO filmPo) {
        String filmName = filmPo.getName();
        String director = filmPo.getDirector();
        String actor = filmPo.getActor();
        LocalDateTime releaseTime = filmPo.getReleaseTime();
        LocalTime duration = filmPo.getDuration();
        String poster = filmPo.getPoster();
        Boolean status = filmPo.getStatus();

        return new FilmModel(filmName, director, actor, releaseTime, duration, poster, status);
    }

    /**
     * 数据库持久化对象集转化为领域中的模型集
     *
     * @param filmPoList 电影订单列表
     * @return {@link List}<{@link FilmModel}>
     */
    public static List<FilmModel> poListToModel(List<FilmPO> filmPoList) {
        ArrayList<FilmModel> filmModels = new ArrayList<>();

        for (FilmPO film : filmPoList) {
            // 利用转换器进行类型转换
            FilmModel filmModel = FilmConvertor.poToModel(film);

            filmModels.add(filmModel);
        }

        return filmModels;
    }
}
