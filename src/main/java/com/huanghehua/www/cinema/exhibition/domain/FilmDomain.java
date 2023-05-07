package com.huanghehua.www.cinema.exhibition.domain;

import com.huanghehua.www.cinema.exhibition.domain.gateway.ShowGateWay;
import com.huanghehua.www.cinema.exhibition.infrastructure.gatewayimpl.ShowGateWayImpl;
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
public class FilmDomain {
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

    public FilmDomain(String name, String director, String actor,
                      LocalDateTime releaseTime, LocalTime duration,
                      String poster) {
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
        this.poster = poster;
    }

    public FilmDomain() {
    }

    // TODO 造成依赖了吗？
    @Reference(ShowGateWayImpl.class)
    private ShowGateWay showGateWay;

    public List<FilmDomain> getFilm(String name, PageAbility pageAbility) {
        return showGateWay.getFilm(name, pageAbility);
    }
    public List<FilmDomain> getFilm(String name) {
        return showGateWay.getFilm(name);
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

    public ShowGateWay getShowGateWay() {
        return showGateWay;
    }

    public void setShowGateWay(ShowGateWay showGateWay) {
        this.showGateWay = showGateWay;
    }
}