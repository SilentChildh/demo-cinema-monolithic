package com.huanghehua.www.cinema.app.convertor;

import com.huanghehua.www.cinema.client.dto.FilmDTO;
import com.huanghehua.www.cinema.client.dto.command.FilmAddCmd;
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
     * 领域中的模型转化为dto模型
     *
     * @param filmModel 电影阿宝
     * @return {@link FilmDTO}
     */
    public static FilmDTO modelToDto(FilmModel filmModel) {
        Long id = filmModel.getId();
        String filmName = filmModel.getName();
        String director = filmModel.getDirector();
        String actor = filmModel.getActor();
        LocalDateTime releaseTime = filmModel.getReleaseTime();
        LocalTime duration = filmModel.getDuration();
        String poster = filmModel.getPoster();

        return new FilmDTO(id, filmName, director, actor, releaseTime, duration, poster);
    }

    /**
     * 领域中的模型集转化为dto模型集
     *
     * @param filmmodelList filmmodel列表
     * @return {@link List}<{@link FilmDTO}>
     */
    public static List<FilmDTO> modelListToDto(List<FilmModel> filmmodelList) {
        ArrayList<FilmDTO> list = new ArrayList<>();

        for (FilmModel film : filmmodelList) {
            // 利用转换器进行类型转换
            FilmDTO filmDTO = FilmConvertor.modelToDto(film);

            list.add(filmDTO);
        }

        return list;
    }

    /**
     * dto模型转化为持久化对象
     *
     * @param filmAddCmd 电影添加cmd
     * @return {@link FilmPO}
     */
    public static FilmPO dtoToPo(FilmAddCmd filmAddCmd) {
        return new FilmPO(filmAddCmd.getName(),
                filmAddCmd.getDirector(),
                filmAddCmd.getActor(),
                filmAddCmd.getReleaseTime(),
                filmAddCmd.getDuration(),
                filmAddCmd.getPoster());
    }

}
