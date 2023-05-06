package com.huanghehua.www.framework.orm.spi.datasource;

/**
 * 数据源工厂
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/04/10
 */
public interface DataSourceFactory {
    /**
     * 用于外界获取连接池对象，可以通过该对象获取连接资源。<br/>
     * <p/>
     *
     * @param resource 连接池配置文件类路径
     * @return {@link DataSource}
     */
    DataSource creatDataSource(String resource);
}

