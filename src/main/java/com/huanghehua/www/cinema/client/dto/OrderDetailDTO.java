package com.huanghehua.www.cinema.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 电影订单详情数据模型
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class OrderDetailDTO {
    /**
     * 影片id
     */
    private Long filmId;
    /**
     * 影片名称，普通索引
     */
    private String filmName;
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
     * 影厅id
     */
    private Long hallId;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 影片场次开始时间
     */
    private LocalDateTime startTime;
    /**
     * 影片场次结束时间
     */
    private LocalDateTime endTime;


    /**
     * 行号
     */
    private Integer row;
    /**
     * 列号
     */
    private Integer column;

    public OrderDetailDTO(Long filmId, String filmName, String director, String actor, LocalDateTime releaseTime, LocalTime duration, String poster, Long hallId, BigDecimal price,
                          LocalDateTime startTime, LocalDateTime endTime, Integer row, Integer column) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
        this.poster = poster;
        this.hallId = hallId;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.row = row;
        this.column = column;
    }

    public OrderDetailDTO(String filmName, String director, String actor,
                          LocalDateTime releaseTime, LocalTime duration, String poster,
                          Long hallId, BigDecimal price, LocalDateTime startTime,
                          LocalDateTime endTime, Integer row, Integer column) {
        this.filmName = filmName;
        this.director = director;
        this.actor = actor;
        this.releaseTime = releaseTime;
        this.duration = duration;
        this.poster = poster;
        this.hallId = hallId;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.row = row;
        this.column = column;
    }

    public OrderDetailDTO(Long filmId, Long hallId, BigDecimal price, LocalDateTime startTime,
                          LocalDateTime endTime, Integer row, Integer column) {
        this.filmId = filmId;
        this.hallId = hallId;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.row = row;
        this.column = column;
    }

    public OrderDetailDTO(Long hallId, BigDecimal price, LocalDateTime startTime,
                          LocalDateTime endTime, Integer row, Integer column) {
        this.hallId = hallId;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDetailDTO that = (OrderDetailDTO) o;
        return Objects.equals(filmId, that.filmId) && Objects.equals(filmName, that.filmName) && Objects.equals(director, that.director) && Objects.equals(actor, that.actor) && Objects.equals(releaseTime, that.releaseTime) && Objects.equals(duration, that.duration) && Objects.equals(poster, that.poster) && Objects.equals(hallId, that.hallId) && Objects.equals(price, that.price) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(row, that.row) && Objects.equals(column, that.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, filmName, director, actor, releaseTime, duration, poster, hallId, price, startTime, endTime, row, column);
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "filmId=" + filmId +
                ", name='" + filmName + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", releaseTime=" + releaseTime +
                ", duration=" + duration +
                ", poster='" + poster + '\'' +
                ", hallId=" + hallId +
                ", price=" + price +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", row=" + row +
                ", column=" + column +
                '}';
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
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

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }
}
