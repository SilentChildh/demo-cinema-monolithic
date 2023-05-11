package com.huanghehua.www.cinema.domain.exhibition;

import com.huanghehua.www.cinema.domain.gateway.ExhibitionGateWay;
import com.huanghehua.www.cinema.infrastructure.gatewayimpl.ExhibitionGateWayImpl;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.common.PageAbility;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 电影模型
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
@Bean
public class FilmModel {
    /**
     * 影片名称，普通索引
     */
    private String name;
    /**
     * 导演
     */
    private String director;
    /**
     * 主演
     */
    private String actor;
    /**
     * 发布日期、时间
     */
    private LocalDateTime releaseTime;
    /**
     * 影片持续时间
     */
    private LocalTime duration;
    /**
     * 海报存储于数据库服务器的绝对路径
     */
    private String poster;

    public FilmModel(String name, String director, String actor,
                     LocalDateTime releaseTime, LocalTime duration,
                     String poster) {
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
        this.poster = poster;
    }

    public FilmModel() {
    }

    // TODO 造成依赖了吗？
    @Reference(ExhibitionGateWayImpl.class)
    private ExhibitionGateWay exhibitionGateWay;

    public List<FilmModel> getFilm(String name, PageAbility pageAbility) {
        return exhibitionGateWay.listPageFilm(name, pageAbility);
    }
    public List<FilmModel> getFilm(String name) {
        return exhibitionGateWay.listFilm(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public ExhibitionGateWay getShowGateWay() {
        return exhibitionGateWay;
    }

    public void setShowGateWay(ExhibitionGateWay exhibitionGateWay) {
        this.exhibitionGateWay = exhibitionGateWay;
    }
}
