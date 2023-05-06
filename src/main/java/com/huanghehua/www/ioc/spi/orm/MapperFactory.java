package com.huanghehua.www.ioc.spi.orm;

/**
 * Mapper工厂，用于获取操作数据库的对象
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/25
 */
public interface MapperFactory {
    /**
     * 根据DAO接口class对象，获取操作数据库的实例
     *
     * @param clazz DAO接口class对象
     * @return {@link T}
     */
    <T> T getMapper(Class<T> clazz);
}
