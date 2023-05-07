package com.huanghehua.www.cinema.exhibition.client;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 电影信息，。用于服务模块之间传递影片数据
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class FilmDTO {
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

    public FilmDTO() {
    }

    public FilmDTO(String name, String director,
                   String actor, LocalDateTime releaseTime,
                   LocalTime duration) {
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmDTO filmDTO = (FilmDTO) o;
        return Objects.equals(name, filmDTO.name) && Objects.equals(director, filmDTO.director) && Objects.equals(actor, filmDTO.actor) && Objects.equals(releaseTime, filmDTO.releaseTime) && Objects.equals(duration, filmDTO.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, director, actor, releaseTime, duration);
    }

    @Override
    public String toString() {
        return "FilmDTO{" +
                "name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", releaseTime=" + releaseTime +
                ", duration=" + duration +
                '}';
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
}
