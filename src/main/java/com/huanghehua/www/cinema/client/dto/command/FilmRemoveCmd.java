package com.huanghehua.www.cinema.client.dto.command;

/**
 * 电影删除cmd
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
public class FilmRemoveCmd {
    /**
     * 影片id
     */
    private Long filmId;

    public FilmRemoveCmd(Long filmId) {
        this.filmId = filmId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }
}
