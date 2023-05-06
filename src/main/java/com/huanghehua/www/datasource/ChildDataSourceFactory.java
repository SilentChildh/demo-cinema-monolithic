package com.huanghehua.www.datasource;

import com.huanghehua.www.datasource.model.DataSourceConfiguration;
import com.huanghehua.www.orm.spi.datasource.DataSource;
import com.huanghehua.www.orm.spi.datasource.DataSourceFactory;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源工厂，调用者将通过该工厂类获取对应的连接池对象。<br/>
 * <p/>
 * 该工厂中含有静态常量的并发Map，该容器将保证统一数据源是单例的。<br/>
 * 利用并发Map，保证多线程安全。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/17
 */
public class ChildDataSourceFactory implements DataSourceFactory {
    /**
     * 数据源容器，用于管理连接池。
     * K为数据源url，但不包含query信息， V为对应的连接池对象
     */
    private final Map<String, ChildDataSource> dataSourceMap = new ConcurrentHashMap<>();
    /**
     * 用于外界获取连接池对象，可以通过该对象获取连接资源。<br/>
     * <p/>
     *
     * @param resource 连接池配置文件类路径
     * @return {@link DataSource} 一个数据库连接池对象
     */
    public DataSource creatDataSource(String resource) {

        // 将资源文件的相关信息保存在资源包中
        ResourceBundle resourceBundle = ResourceBundle.getBundle(resource);
        // 先获取url
        String url = resourceBundle.getString(ConfigConstants.URL);
        String id = url.substring(0, url.indexOf('?'));
        // 如果容器中存在该数据源，直接返回
        if (dataSourceMap.containsKey(id)) {
            return dataSourceMap.get(id);
        }


        // 继续获取对应资源
        String driver = resourceBundle.getString(ConfigConstants.DRIVER);
        String user = resourceBundle.getString(ConfigConstants.USER);
        String password = resourceBundle.getString(ConfigConstants.PASSWORD);
        int initialSize = Integer.parseInt(resourceBundle.getString(ConfigConstants.INITIAL_SIZE));
        int minIdle = Integer.parseInt(resourceBundle.getString(ConfigConstants.MIN_IDLE));
        int maxActive = Integer.parseInt(resourceBundle.getString(ConfigConstants.MAX_ACTIVE));
        int maxWait = Integer.parseInt(resourceBundle.getString(ConfigConstants.MAX_WAIT));

        // 通过构造器创建连接池配置对象
        DataSourceConfiguration dataSourceConfiguration =
                new DataSourceConfiguration(driver, url, user, password, initialSize, minIdle, maxActive, maxWait);

        ChildDataSource childDataSource = new ChildDataSource(dataSourceConfiguration).init();

        // // 上锁，以免破坏单例
        synchronized (dataSourceMap) {
            if (!dataSourceMap.containsKey(id)) {
                dataSourceMap.put(id, childDataSource);
            }
        }

        // 需要从容器中获取，否则可能还是拿到不同的连接池对象
        return dataSourceMap.get(id);
    }

    /**
     * 用于囊括 将配置信息作为静态常量的静态类
     */
    static class ConfigConstants {
        private static final String DRIVER = "driver";
        private static final String URL = "url";
        private static final String USER = "username";
        private static final String PASSWORD = "password";
        private static final String INITIAL_SIZE = "initialSize";
        private static final String MIN_IDLE = "minIdle";
        private static final String MAX_ACTIVE = "maxActive";
        private static final String MAX_WAIT = "maxWait";
    }
}
