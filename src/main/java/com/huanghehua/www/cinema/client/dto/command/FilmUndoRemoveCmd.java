package com.huanghehua.www.cinema.client.dto.command;

import java.util.Objects;

/**
 * 电影撤消删除cmd
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/15
 */
public class FilmUndoRemoveCmd {
    /**
     * 影片id
     */
    private Long filmId;

    @Override
    public String toString() {
        return "FilmUndoRemoveCmd{" +
                "filmId=" + filmId +
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
        FilmUndoRemoveCmd that = (FilmUndoRemoveCmd) o;
        return Objects.equals(filmId, that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId);
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public FilmUndoRemoveCmd() {
    }

    public FilmUndoRemoveCmd(Long filmId) {
        this.filmId = filmId;
    }
}
