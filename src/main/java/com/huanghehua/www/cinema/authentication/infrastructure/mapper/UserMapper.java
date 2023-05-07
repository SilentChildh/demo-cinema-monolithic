package com.huanghehua.www.cinema.authentication.infrastructure.mapper;

import com.huanghehua.www.cinema.authentication.infrastructure.data.UserPO;
import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Mapper;
import com.huanghehua.www.orm.annotation.Param;

/**
 * 对数据库中User表进行操作
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/26
 */
@Bean
@Mapper
public interface UserMapper {
    /**
     * 新增一个用户
     *
     * @param email 邮箱信息
     * @param password 密码信息
     * @return int
     */
    int insertUser(@Param("email") String email, @Param("password") String password);

    /**
     * 根据邮箱查询某个用户信息
     *
     * @param email    邮箱
     * @param password 密码
     * @return {@link UserPO}
     */
    UserPO selectSingleUserByEmail(@Param("email") String email, @Param("password") String password);

}
