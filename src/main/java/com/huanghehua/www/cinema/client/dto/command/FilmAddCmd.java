package com.huanghehua.www.cinema.client.dto.command;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 电影添加cmd
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
public class FilmAddCmd {
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

    public FilmAddCmd() {
    }

    public FilmAddCmd(String name, String director, String actor,
                      LocalDateTime releaseTime, LocalTime duration, String poster) {
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmAddCmd that = (FilmAddCmd) o;
        return Objects.equals(name, that.name) && Objects.equals(director, that.director) && Objects.equals(actor, that.actor) && Objects.equals(releaseTime, that.releaseTime) && Objects.equals(duration, that.duration) && Objects.equals(poster, that.poster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, director, actor, releaseTime, duration, poster);
    }

    @Override
    public String toString() {
        return "FilmAddCmd{" +
                "name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", releaseTime=" + releaseTime +
                ", duration=" + duration +
                ", poster='" + poster + '\'' +
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
