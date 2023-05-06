package com.huanghehua.www.orm.spi.datasource;

import java.sql.Connection;

/**
 * 数据源
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/10
 */
public interface DataSource {

    /**
     * 用于开启连接池对象。<br/>
     * <p/>
     *
     * @return {@link DataSource} 一个数据库连接池对象
     */
    DataSource init();
    /**
     * 用于从空闲池中获取连接资源。<br/>
     * <p/>
     * 以{@code queue}为锁，防止多线程并发获取连接。<br/>
     * 特别地，由于最小空闲数的存在，基本上都不会令阻塞队列在获取资源时发生阻塞。<br/>
     * 之所以要用一个线程来监视获取资源，是因为在本次操作中，实际上可以分为两层：
     * 拿不到第一把锁的所有线程将进入阻塞，假如已获得第一把锁的线程在想得到第二把锁的时候被阻塞，那么就会有死锁的可能性。
     * 故此时通过提供一个外部线程，无论如何都会在一定时间后使其释放锁，即通知他们超时。<br/>
     *
     * @return {@link Connection}
     */
    Connection getConnection();

    /**
     * 用于回收资源到空闲池<br/>
     *
     * @param connection 需要回收的连接资源
     * @return {@link Void}
     */
    Void release(Connection connection);

    /**
     * 物理关闭连接池中的所有连接资源
     */
    void destroy();
}
