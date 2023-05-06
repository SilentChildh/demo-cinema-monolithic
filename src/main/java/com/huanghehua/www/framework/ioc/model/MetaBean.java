package com.huanghehua.www.framework.ioc.model;


import com.huanghehua.www.ioc.annotation.Bean;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 用于存储bean实例的相关信息。<br/>
 * <p/>
 * 该对于{@link MetaBean}来说，其实也是一个bean实例，只不过该bean实例不被IoC容器所管理。<br/>
 * 该实例仅用于保存实际应用中bean实例的相关信息，即可以理解为bean实例的元数据<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/14
 */
public class MetaBean {
    /**
     * 处于注入状态中，用于解决依赖循环问题，true表示正在注入。
     */
    private AtomicBoolean injecting = new AtomicBoolean();
    /**
     * id用于表示全限定类名
     */
    private String id;

    /**
     * 对应bean的class对象
     */
    private Class<?> clazz;

    private Bean.BeanScope scope;
    /**
     * 对应bean字段上引用的其他bean实例的class对象.
     * K为字段名，V为引用的实例的类对象
     */
    private Map<String, Class<?>> references;

    /**
     * 保存对应bean实例每个字段的简单值。
     * K为字段名，V为字段值
     */
    private Map<String, String> properties;
    /**
     * 保存对应bean实例构造器的实参值
     * K为形参名，V为字段值
     */
    private Map<String, String> constructorArgs;

    public AtomicBoolean getInjecting() {
        return injecting;
    }

    public void setInjecting(boolean injecting) {
        this.injecting.set(injecting);
    }

    public MetaBean() {
    }

    @Override
    public String toString() {
        return "MetaBean{" +
                "injecting=" + injecting +
                ", id='" + id + '\'' +
                ", clazz=" + clazz +
                ", scope=" + scope +
                ", references=" + references +
                ", properties=" + properties +
                ", constructorArgs=" + constructorArgs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetaBean metaBean = (MetaBean) o;
        return Objects.equals(injecting, metaBean.injecting) && Objects.equals(id, metaBean.id) && Objects.equals(clazz, metaBean.clazz) && scope == metaBean.scope && Objects.equals(references, metaBean.references) && Objects.equals(properties, metaBean.properties) && Objects.equals(constructorArgs, metaBean.constructorArgs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(injecting, id, clazz, scope, references, properties, constructorArgs);
    }

    public void setInjecting(AtomicBoolean injecting) {
        this.injecting = injecting;
    }

    public Bean.BeanScope getScope() {
        return scope;
    }

    public void setScope(Bean.BeanScope scope) {
        this.scope = scope;
    }

    public MetaBean(String id, Class<?> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public MetaBean(String id, Class<?> clazz, Map<String, Class<?>> references,
                    Map<String, String> properties) {
        this.id = id;
        this.clazz = clazz;
        this.references = references;
        this.properties = properties;
    }

    public Map<String, Class<?>> getReferences() {
        return references;
    }

    public void setReferences(Map<String, Class<?>> references) {
        this.references = references;
    }

    public Map<String, String> getConstructorArgs() {
        return constructorArgs;
    }

    public void setConstructorArgs(Map<String, String> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

}
