package com.huanghehua.www.cinema.authentication.web.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 用户信息数据值信息，用于前后端之间传递信息
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public class UserVO implements Serializable {
    private static final long serialVersionUID = -1130874931912964078L;
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

    public UserVO(String email, String password, Boolean status) {
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
        UserVO userVO = (UserVO) o;
        return Objects.equals(email, userVO.email) && Objects.equals(password, userVO.password) && Objects.equals(status, userVO.status);
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
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
