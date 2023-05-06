package com.huanghehua.www.framework.orm;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务管理器接口，定义了管理事务的相关方法。<br/>
 * <p/>
 * 需要注意的是，实现该接口的所有方法将不处理任何异常，直接向上抛出交由调用者处理。<br/>
 * 注意，每一个{@link SqlSession}都应该持有一个全新的事务管理器。<br/>
 * 事务管理器的作用域也应该是请求域范围。<br/>
 *
 * @author silent_child
 * @version 1.0
 **/

public interface Transaction {
    /**
     * 获得连接。<br/>
     * 通过调用{@code openConnection()}方法来开启资源，再返回获取得到的连接资源
     *
     * @return {@link Connection} 返回一个连接
     * @throws SQLException sqlexception异常，直接向上抛出
     */
    Connection getConnection() throws SQLException;

    /**
     * 关闭
     * 用于关闭连接资源
     *
     * @throws SQLException sqlexception异常，直接向上抛出
     */
    void close() throws SQLException;

    /**
     * 提交
     * 用于提交事务
     *
     * @throws SQLException sqlexception异常，直接向上抛出
     */
    void commit() throws SQLException;

    /**
     * 用于回滚事务
     *
     * @throws SQLException sqlexception异常，直接向上抛出
     */
    void rollback() throws SQLException;

}
