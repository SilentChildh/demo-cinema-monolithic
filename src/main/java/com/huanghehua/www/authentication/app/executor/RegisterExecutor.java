package com.huanghehua.www.authentication.app.executor;

import com.huanghehua.www.authentication.api.Register;
import com.huanghehua.www.authentication.dto.UserDTO;
import com.huanghehua.www.common.CommonResult;

/**
 * 注册业务执行器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
public class RegisterExecutor implements Register {
    @Override
    public CommonResult<UserDTO> register(String email, String password) {
        return null;
    }
}
