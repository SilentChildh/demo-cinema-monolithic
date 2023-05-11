package com.huanghehua.www.cinema.client.dto;

import com.huanghehua.www.annotation.Api;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 座位信息，用于服务之间传输数据
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/07
 */
public class SeatDTO {
    @Api
    private FilmDTO filmDto;
    /**
     * 影片场次开始时间
     */
    private LocalDateTime startTime;
    /**
     * 影片场次结束时间
     */
    private LocalDateTime endTime;
    /**
     * 场次可容纳实际人数
     */
    private Integer capacity;

    public SeatDTO(FilmDTO filmDto,
                   LocalDateTime startTime,
                   LocalDateTime endTime, Integer capacity) {
        this.filmDto = filmDto;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public SeatDTO() {
    }

    @Override
    public String toString() {
        return "SeatDTO{" +
                "filmDto=" + filmDto +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", capacity=" + capacity +
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
        SeatDTO seatDTO = (SeatDTO) o;
        return Objects.equals(filmDto, seatDTO.filmDto) && Objects.equals(startTime, seatDTO.startTime) && Objects.equals(endTime, seatDTO.endTime) && Objects.equals(capacity, seatDTO.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmDto, startTime, endTime, capacity);
    }

    public FilmDTO getFilmDto() {
        return filmDto;
    }

    public void setFilmDto(FilmDTO filmDto) {
        this.filmDto = filmDto;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
