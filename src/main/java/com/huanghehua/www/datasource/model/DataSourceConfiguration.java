package com.huanghehua.www.datasource.model;

/**
 * 数据源配置
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/22
 */
public class DataSourceConfiguration {

    private String driver;
    private String url;
    private String user;
    private String password;
    /**
     * 初始化连接数，默认为10
     */
    private int initialSize;
    /**
     * 空闲池中最少连接资源数量，默认为5
     */
    private int minIdle;
    /**
     * 活跃状态下的最大连接数，默认为20
     */
    private int maxActive;
    /**
     * 最大连接时长，默认是5s
     */
    private int maxWait;

    /**
     * 私有化，放置外界通过构造器创建连接池对象。<br/>
     *
     * @param driver      驱动
     * @param url         资源位置
     * @param user        数据库用户名
     * @param password    数据库密码
     * @param initialSize 初始化连接资源数量
     * @param minIdle     连接资源的最小空闲数量
     * @param maxActive   连接资源的最大活跃数量
     * @param maxWait     获取连接资源的最大耗时
     */
    public DataSourceConfiguration(String driver, String url, String user, String password,
                                   int initialSize, int minIdle, int maxActive, int maxWait) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
        this.initialSize = initialSize;
        this.minIdle = minIdle;
        this.maxActive = maxActive;
        this.maxWait = maxWait;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }
}
