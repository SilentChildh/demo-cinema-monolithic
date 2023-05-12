package com.huanghehua.www.cinema.client.dto;

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
     * id
     */
    private Long id;
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

    public FilmDTO(Long id, String name, String director, String actor,
                     LocalDateTime releaseTime, LocalTime duration, String poster) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
        this.poster = poster;
    }

    public FilmDTO(String name, String director, String actor,
                     LocalDateTime releaseTime, LocalTime duration,
                     String poster) {
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
        this.poster = poster;
    }

    public FilmDTO() {
    }

    @Override
    public String toString() {
        return "FilmDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", releaseTime=" + releaseTime +
                ", duration=" + duration +
                ", poster='" + poster + '\'' +
                '}';
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
        return Objects.equals(id, filmDTO.id) && Objects.equals(name, filmDTO.name) && Objects.equals(director, filmDTO.director) && Objects.equals(actor, filmDTO.actor) && Objects.equals(releaseTime, filmDTO.releaseTime) && Objects.equals(duration, filmDTO.duration) && Objects.equals(poster, filmDTO.poster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, director, actor, releaseTime, duration, poster);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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
