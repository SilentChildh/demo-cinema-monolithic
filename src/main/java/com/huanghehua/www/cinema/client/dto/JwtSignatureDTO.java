package com.huanghehua.www.cinema.client.dto;

import java.util.Objects;

/**
 * 含JWT的数字签名
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public class JwtSignatureDTO {
    /**
     * 用户id
     */
    private Long userId;
    private String jwt;

    public JwtSignatureDTO() {
    }

    public JwtSignatureDTO(Long userId, String jwt) {
        this.userId = userId;
        this.jwt = jwt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JwtSignatureDTO that = (JwtSignatureDTO) o;
        return Objects.equals(userId, that.userId) && Objects.equals(jwt, that.jwt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, jwt);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "JwtSignatureDTO{" +
                "userId=" + userId +
                ", jwt='" + jwt + '\'' +
                '}';
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}
