package com.huanghehua.www.cinema.client.dto.command;

import java.util.Objects;

/**
 * 用户登录cmd
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
public class UserLoginCmd {
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 密码
     */
    private String password;

    public UserLoginCmd() {
    }

    public UserLoginCmd(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginCmd{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
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
        UserLoginCmd that = (UserLoginCmd) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
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
}
