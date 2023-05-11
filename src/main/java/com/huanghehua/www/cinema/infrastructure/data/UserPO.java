package com.huanghehua.www.cinema.infrastructure.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 对应数据库中用户表的实体。
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/22
 */
public class UserPO implements Serializable {
    private static final long serialVersionUID = -3347852462889844247L;
    /**
     * 逻辑id，主键索引
     */
    private Long id;
    /**
     * 用户的邮箱，用于标识唯一的用户，唯一索引
     */
    private String email;
    /**
     * 用户的密码，用于配合邮箱登录我域名之下的系统
     */
    private String password;
    /**
     * 用户的状态，true为启用，false为禁用
     */
    private Boolean status;
    /**
     * 用户账户创建的时间
     */
    private LocalDateTime createTime;
    /**
     * 用户账户信息的更新时间
     */
    private LocalDateTime updateTime;

    public UserPO() {
    }

    public UserPO(String email, String password, Boolean status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPO user = (UserPO) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(status, user.status) && Objects.equals(createTime, user.createTime) && Objects.equals(updateTime, user.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, status, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }


    public Boolean getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
