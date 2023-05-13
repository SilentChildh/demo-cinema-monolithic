package com.huanghehua.www.cinema.client.dto.command;

import java.util.Objects;

/**
 * 电影场次删除cmd
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
public class ScheduleRemoveCmd {
    /**
     * 场次id
     */
    private Long scheduleId;

    public ScheduleRemoveCmd(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public ScheduleRemoveCmd() {
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScheduleRemoveCmd that = (ScheduleRemoveCmd) o;
        return Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId);
    }

    @Override
    public String toString() {
        return "ScheduleRemoveCmd{" +
                "scheduleId=" + scheduleId +
                '}';
    }

}
