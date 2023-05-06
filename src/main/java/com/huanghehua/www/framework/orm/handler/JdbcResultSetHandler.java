package com.huanghehua.www.framework.orm.handler;

import com.huanghehua.www.orm.exception.ReflectionException;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 结果集处理器，在对数据库进行查询操作时，可以通过不同的策略而选择不同的结果集处理方法。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/17
 */

public class JdbcResultSetHandler {
    /**
     * 用于将结果集中的记录封装到指定返回值类型的元素中，
     * 并将元素放入List集合中。最后返回一个List集合。<br/>
     * <p/>
     * 该方法主要分三步。第一步，遍历所有记录。第二步，对每一个记录进行处理。第三步，返回List集合。<br/>
     * 接下来重点将第二步的操作。该步骤中，首先需要创建一个实例来接收结果集中一条记录中的数据。
     * 然后开始遍历记录中的每一列信息，通过反射获取实例中的字段，并对其进行赋值。最后将实例放入List集合中。<br/>
     * 重复第二步直到遍历所有记录。
     *
     * @param resultSet  指定结果集
     * @param resultType 返回值类型的class对象，即E的class对象
     * @return {@link List}<{@link E}> 存放着封装了结果集数据的指定元素E的List集合
     * @throws SQLException 直接向上抛出
     */
    public static <E> List<E> handleToList (ResultSet resultSet, Class<?> resultType) throws SQLException {
        // List集合，用于接收封装了结果集数据的E类型的元素
        List<E> list = new ArrayList<>(64);
        // 获得结果集元信息
        ResultSetMetaData metaData = resultSet.getMetaData();
        // 获取结果集列数
        int columnCount = metaData.getColumnCount();

        // 判断是否是单列
        List<E> jdkType = jdbcType(resultSet);
        if (jdkType != null) {
            list.addAll(jdkType);
            return list;
        }

        /*接下来遍历所有记录*/
        while (resultSet.next()) {
            // 创建一个实例，用于装载该条记录的信息
            Object object;
            try {
                object = resultType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.log(Level.SEVERE, "创建对应列元素实例失败", e);
                throw new ReflectionException("创建对应列元素实例失败", e);
            }

            // 遍历每个记录中的每一列
            for (int i = 1; i <= columnCount; i++) {
                // 获取该列的字段名
                String columnName = metaData.getColumnName(i);

                /*接下来得到符合驼峰命名的字段名*/
                StringBuilder stringBuilder = new StringBuilder(columnName);
                int end;
                while (true) {
                    end = stringBuilder.indexOf("_");
                    if (end == -1) {
                        break;
                    }
                    stringBuilder.replace(end, end + 2, String.valueOf((char)(stringBuilder.charAt(end + 1) - 32)));
                }
                // 获取符合驼峰命名的字段名
                String filedName = String.valueOf(stringBuilder);

                /*接下来将记录装载到实例中*/
                try {

                    // 获取字段
                    Field field = resultType.getDeclaredField(filedName);
                    // 设置为可访问
                    field.setAccessible(true);

                    // 获取指定字段下的记录值
                    Object value = resultSet.getObject(columnName);

                    // 尝试转换
                    value = formatLocalDateOrLocalTime(value);

                    // 赋值
                    field.set(object, value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    LOGGER.log(Level.SEVERE, "返回值类型的字段不存在", e);
                    throw new ReflectionException("返回值类型的字段不存在", e);
                }

            }
            /*最后将装载记录的Object对象放入List集合中*/
            list.add((E) object);
        }

        LOGGER.log(Level.INFO, "结果集收集为List集合成功");
        return list;
    }


    /**
     * 当返回的结果集仅有单列时，且如果是jdbc中定义的数据类型，那么将进行转换为java中的类型。
     *
     * @param resultSet 结果集
     * @return {@link List}<{@link E}>
     * @throws SQLException sqlexception异常
     */
    private static <E> List<E> jdbcType(ResultSet resultSet) throws SQLException {
        List<E> list = new ArrayList<>(64);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        if (columnCount != 1) {
            return null;
        }

        while (resultSet.next()) {
            Object value = resultSet.getObject(1);
            value = formatLocalDateOrLocalTime(value);
            list.add((E) value);
        }
        return list;
    }


    /**
     * 如果是java.sql包下的日期或时间，那么格式化为java.time包下的Local类型。
     * 否则不做任何处理。<br/>
     *
     * @param value 需要格式化的日期和时间
     * @return {@link Object}
     */
    private static Object formatLocalDateOrLocalTime(Object value) {
        if (value instanceof Date) {
            Date date = (Date) value;
            value = LocalDate.from(new Timestamp(date.getTime()).toLocalDateTime());
        }
        else if (value instanceof Time) {
            Time time = (Time) value;
            value = LocalTime.from(new Timestamp(time.getTime()).toLocalDateTime());
        }
        return value;
    }
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.orm.handler.ResultHandler");
}
