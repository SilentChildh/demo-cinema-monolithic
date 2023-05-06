package com.huanghehua.www.authentication.client.api;

import com.huanghehua.www.authentication.dto.SignatureDTO;
import com.huanghehua.www.authentication.dto.UserDTO;
import com.huanghehua.www.common.CommonResult;

/**
 * 注册
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public interface Register {
    /**
     * 注册，返回一个含有用户信息的结果
     * @param email 邮箱
     * @param password 密码
     * @return {@link CommonResult}<{@link SignatureDTO}>
     */
    CommonResult<UserDTO> register(String email, String password);
}
