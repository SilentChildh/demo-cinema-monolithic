package com.huanghehua.www.cinema.client.api;

import com.huanghehua.www.cinema.client.dto.UserDTO;
import com.huanghehua.www.cinema.client.dto.command.UserPasswordUpdateCmd;
import com.huanghehua.www.common.CommonResult;

/**
 * 用户服务
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
public interface UserServiceI {
    /**
     * 设置个人信息
     *
     * @param userPasswordUpdateCmd 用户更新cmd
     * @return {@link CommonResult}<{@link UserDTO}>
     */
    CommonResult<UserDTO> setUserPassword(UserPasswordUpdateCmd userPasswordUpdateCmd);
}
