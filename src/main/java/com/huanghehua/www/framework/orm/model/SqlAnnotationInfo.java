package com.huanghehua.www.framework.orm.model;

import java.util.Objects;

/**
 * sql注解信息，具体可见 {@link com.huanghehua.www.framework.orm.annotation.SimpleSql}
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/22
 */
public class SqlAnnotationInfo {
    private String sql;
    private Class<?> resultType;

    public SqlAnnotationInfo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SqlAnnotationInfo sqlAnnotationInfo = (SqlAnnotationInfo) o;
        return Objects.equals(sql, sqlAnnotationInfo.sql) && Objects.equals(resultType, sqlAnnotationInfo.resultType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sql, resultType);
    }

    @Override
    public String toString() {
        return "Bundle{" +
                "sql='" + sql + '\'' +
                ", resultType=" + resultType +
                '}';
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(Class<?> resultType) {
        this.resultType = resultType;
    }

    public SqlAnnotationInfo(String sql, Class<?> resultType) {
        this.sql = sql;
        this.resultType = resultType;
    }
}
