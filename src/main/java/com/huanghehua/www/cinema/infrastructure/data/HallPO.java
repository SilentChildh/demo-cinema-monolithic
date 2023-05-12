package com.huanghehua.www.cinema.infrastructure.data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 影厅持久化对象
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/11
 */
public class HallPO {
    /**
     * id
     */
    private Long id;
    /**
     * 可容纳实际人数
     */
    private Integer capacity;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    public HallPO(Long id, Integer capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public HallPO(Long id, Integer capacity, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.capacity = capacity;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public HallPO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HallPO hallPO = (HallPO) o;
        return Objects.equals(id, hallPO.id) && Objects.equals(capacity, hallPO.capacity) && Objects.equals(createTime, hallPO.createTime) && Objects.equals(updateTime, hallPO.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "HallPO{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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
