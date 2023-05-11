package com.huanghehua.www.cinema.client.dto;

import java.util.Objects;

/**
 * 含JWT的数字签名
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public class SignatureDTO {
    private String jwt;

    public SignatureDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
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
        SignatureDTO signatureDTO = (SignatureDTO) o;
        return Objects.equals(jwt, signatureDTO.jwt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwt);
    }

    @Override
    public String toString() {
        return "Signature{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}
