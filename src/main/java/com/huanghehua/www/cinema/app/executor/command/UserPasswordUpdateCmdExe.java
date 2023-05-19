package com.huanghehua.www.cinema.app.executor.command;

import com.huanghehua.www.cinema.client.dto.UserDTO;
import com.huanghehua.www.cinema.client.dto.command.UserPasswordUpdateCmd;
import com.huanghehua.www.cinema.infrastructure.mapper.UserMapper;
import com.huanghehua.www.common.CommonResult;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.spi.aop.Interceptable;

/**
 * 用户更新cmd 的executor
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/05/13
 */
@Bean
@Interceptable
public class UserPasswordUpdateCmdExe {

    @Reference
    private UserMapper userMapper;

    /**
     * 通过用户id，修改密码.<br/>
     * <p/>
     * 将会对比旧密码是否一致。
     * 成功则返回DTO数据，否则返回失败提示信息
     *
     * @param userPasswordUpdateCmd 用户密码更新cmd
     * @return {@link CommonResult}<{@link UserDTO}>
     */
    public CommonResult<UserDTO> execute(UserPasswordUpdateCmd userPasswordUpdateCmd) {
        // 获取cmd中的数据
        Long userId = userPasswordUpdateCmd.getUserId();
        String email = userPasswordUpdateCmd.getEmail();
        String oldPassword = userPasswordUpdateCmd.getOldPassword();
        String newPassword = userPasswordUpdateCmd.getNewPassword();

        // 对比旧密码是否一致
        String oldPasswordFromDb = userMapper.getPasswordById(userId);
        int success = 0;

        synchronized (UserPasswordUpdateCmdExe.class) {
            if (!oldPasswordFromDb.equals(oldPassword)) {
                return CommonResult.operateFail("旧密码错误，请重新尝试...");
            }

            // 进行密码修改
            success = userMapper.updatePasswordById(userId, newPassword);
        }

        // 返回成功结果
        return success > 0 ?
                CommonResult.operateSuccess(new UserDTO(email, newPassword)) :
                CommonResult.operateFail("修改失败，请重新尝试...");
    }
}
