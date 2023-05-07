package com.huanghehua.www.cinema.authentication.client.dto;

import java.util.Objects;

/**
 * 用户信息，用于服务内部与外界进行数据传输
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public class UserDTO {
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

    public UserDTO(String email, String password, Boolean status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
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
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(email, userDTO.email) && Objects.equals(password, userDTO.password) && Objects.equals(status, userDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, status);
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
