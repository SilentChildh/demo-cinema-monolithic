package com.huanghehua.www.cinema.infrastructure.data;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 电影信息持久化对象
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class FilmPO {
    /**
     * 逻辑id，主键索引
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
    /**
     * 电影状态，下映时为false，上映时为true
     */
    private Boolean status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public FilmPO() {
    }

    public FilmPO(Long id, String name, String director, String actor, LocalDateTime releaseTime, LocalTime duration,
                  String poster, Boolean status, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
        this.poster = poster;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public FilmPO(String name, String director, String actor, LocalDateTime releaseTime,
                  LocalTime duration, String poster, Boolean status) {
        this.name = name;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
        this.poster = poster;
        this.status = status;
    }

    public FilmPO(String name, String director, String actor,
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
        FilmPO filmPO = (FilmPO) o;
        return Objects.equals(id, filmPO.id) && Objects.equals(name, filmPO.name) && Objects.equals(director, filmPO.director) && Objects.equals(actor, filmPO.actor) && Objects.equals(releaseTime, filmPO.releaseTime) && Objects.equals(duration, filmPO.duration) && Objects.equals(poster, filmPO.poster) && Objects.equals(status, filmPO.status) && Objects.equals(createTime, filmPO.createTime) && Objects.equals(updateTime, filmPO.updateTime);
    }

    @Override
    public String toString() {
        return "FilmPO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", releaseTime=" + releaseTime +
                ", duration=" + duration +
                ", poster='" + poster + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, director, actor, releaseTime, duration, poster, status, createTime, updateTime);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
