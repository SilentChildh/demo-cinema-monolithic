package com.huanghehua.www.cinema.client.dto.command;

import java.util.Objects;

/**
 * 用户更新command
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
public class UserPasswordUpdateCmd {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String newPassword;

    public UserPasswordUpdateCmd(Long userId, String email, String oldPassword, String newPassword) {
        this.userId = userId;
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public UserPasswordUpdateCmd() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPasswordUpdateCmd that = (UserPasswordUpdateCmd) o;
        return Objects.equals(userId, that.userId) && Objects.equals(email, that.email) && Objects.equals(oldPassword, that.oldPassword) && Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, oldPassword, newPassword);
    }

    @Override
    public String toString() {
        return "UserPasswordUpdateCmd{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
