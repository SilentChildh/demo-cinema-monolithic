package com.huanghehua.www.ioc.xml;

import com.huanghehua.www.ioc.exception.ReflectionException;
import com.huanghehua.www.ioc.model.MetaBean;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 解析保存相关bean实例的信息的xml文件。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/14
 */
public class ParseBeanHandler extends DefaultHandler {
    /**
     * bean元信息
     */
    private MetaBean metaBean;
    /**
     * bean的全限定id
     */
    private String id;
    /**
     * 对应bean字段上引用的其他bean实例的class对象.
     * K为字段名，V为引用的实例的全限定类名
     */
    private Map<String, Class<?>> references;
    /**
     * 保存对应bean实例每个字段的值
     */
    private Map<String, String> properties;
    /**
     * 保存对应bean实例构造器的实参值
     */
    private Map<String, String> constructorArgs;
    /**
     * 存储所有bean实例的元信息，K为全限定id，V对应bean的元信息
     */
    private final Map<String, MetaBean> metaBeanMap = new HashMap<>();
    /**
     * 类加载器，用于得到class对象
     */
    private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    public Map<String, MetaBean> getMetaBeanMap() {
        return metaBeanMap;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (BEAN.equals(qName)) {
            // 新建一个MetaBean，以及三个Map
            metaBean = new MetaBean();
            references = new HashMap<String, Class<?>>(64);
            properties = new HashMap<>(64);
            constructorArgs = new HashMap<>(64);

            // 设置全限定类名
            id = attributes.getValue(ID);
            metaBean.setId(id);

            // 通过全限定类名设置class对象
            try {
                Class<?> aClass = CLASS_LOADER.loadClass(id);
                metaBean.setClazz(aClass);
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "ClassNotFound，类名错误", e);
                throw new ReflectionException("ClassNotFound，类名错误", e);
            }

        }
        if (PROPERTY.equals(qName)) {
            // 设置依赖值
            String name = attributes.getValue(NAME);
            String value = attributes.getValue(VALUE);
            String refName = attributes.getValue(REFERENCE);
            Class<?> reference = null;
            try {
                if (refName != null) {
                    reference = Class.forName(refName);
                }
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "ReferenceClassNotFoundException", e);
                throw new ReflectionException("ReferenceClassNotFoundException", e);
            }

            // 若是简单值
            if (value != null) {
                properties.put(name, value);
            }
            // 若是引用值
            references.put(name, reference);
        }

        if (CONSTRUCTOR_ARGS.equals(qName)) {

            String name = attributes.getValue(NAME);
            String value = attributes.getValue(VALUE);
            constructorArgs.put(name, value);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (BEAN.equals(qName)) {
            metaBean.setReferences(references);
            metaBean.setProperties(properties);
            metaBean.setConstructorArgs(constructorArgs);
            // 将metaBean放入Map中
            metaBeanMap.put(id, metaBean);
        }
    }

    private static final String BEAN = "bean";
    private static final String ID = "id";
    private static final String PROPERTY = "property";
    private static final String CONSTRUCTOR_ARGS = "constructor-args";
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String REFERENCE = "ref";

    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.ioc.xml.ParseBeanHandler");

}
