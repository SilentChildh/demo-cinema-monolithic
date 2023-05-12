package com.huanghehua.www.cinema.app.convertor;

import com.huanghehua.www.cinema.client.dto.ScheduleDTO;
import com.huanghehua.www.cinema.infrastructure.data.SchedulePO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 场次数据模型 转换器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/12
 */
public class ScheduleConvertor {
    /**
     * 数据库持久化数据模型转化为dto模型
     *
     * @param schedulePo 安排订单
     * @return {@link ScheduleDTO}
     */
    public static ScheduleDTO poToDto(SchedulePO schedulePo) {
        Long id = schedulePo.getId();
        Long filmId = schedulePo.getFilmId();
        Long hallId = schedulePo.getHallId();
        BigDecimal price = schedulePo.getPrice();
        LocalDateTime startTime = schedulePo.getStartTime();
        LocalDateTime endTime = schedulePo.getEndTime();

        return new ScheduleDTO(id, filmId, hallId, price, startTime, endTime);
    }

    /**
     * 数据库持久化数据模型集转化为dto模型集
     *
     * @param schedulePoList 安排订单列表
     * @return {@link List}<{@link ScheduleDTO}>
     */
    public static List<ScheduleDTO> poListToDto(List<SchedulePO> schedulePoList) {
        ArrayList<ScheduleDTO> list = new ArrayList<>();

        for (SchedulePO schedulePo : schedulePoList) {
            // 利用转换器进行类型转换
            ScheduleDTO scheduleDTO = ScheduleConvertor.poToDto(schedulePo);

            list.add(scheduleDTO);
        }
        return list;
    }
}
