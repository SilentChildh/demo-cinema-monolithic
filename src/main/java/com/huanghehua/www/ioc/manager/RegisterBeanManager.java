package com.huanghehua.www.ioc.manager;

import com.huanghehua.www.ioc.annotation.Bean;
import com.huanghehua.www.ioc.annotation.Reference;
import com.huanghehua.www.ioc.annotation.Value;
import com.huanghehua.www.ioc.exception.ParseException;
import com.huanghehua.www.ioc.exception.ReflectionException;
import com.huanghehua.www.ioc.model.MetaBean;
import com.huanghehua.www.ioc.util.ParseXmlUtils;
import com.huanghehua.www.ioc.util.ScannerUtils;
import com.huanghehua.www.ioc.xml.ParseBeanHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * bean实例的注册器
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/22
 */
public class RegisterBeanManager {
    /**
     * 类加载器，用于得到class对象
     */
    private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    /**
     * Bean注解的class对象
     */
    private static final Class<Bean> BEAN_ANNOTATION_CLASS = Bean.class;

    /**
     * Value注解的class对象
     */
    private static final Class<Value> VALUE_ANNOTATION_CLASS = Value.class;
    /**
     * Reference注解的class对象
     */
    private static final Class<Reference> REFERENCE_ANNOTATION_CLASS = Reference.class;

    /**
     * 通过包含配置文件的全限定包名将对应的元bean注册到{@code metaBeanMap}容器中。<br/>
     *
     * @param packageName 全限定包名
     */
    public static Map<String, MetaBean> registerMetaBeanFromXml(String packageName) {
        Map<String, MetaBean> metaBeanMap = new HashMap<>(1024);
        // 获取解析器
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser;

        /*开始解析xml文件*/

        try {
            // 开启解析
            saxParser = saxParserFactory.newSAXParser();
            ParseBeanHandler parseBeanHandler = new ParseBeanHandler();
            // 获取xml文件
            List<File> xmlFiles = ParseXmlUtils.getXmlFileFromPackage(packageName);

            // 得到元bean，并加入到metaBeanMap容器中
            for (File file : xmlFiles) {
                saxParser.parse(file, parseBeanHandler);

                metaBeanMap.putAll(parseBeanHandler.getMetaBeanMap());
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.log(Level.SEVERE, "解析bean文件Fail", e);
            throw new ParseException( "解析bean文件Fail", e);
        }
        // 返回元BeanMap
        return metaBeanMap;
    }

    /**
     * 通过全限定包名，将包下被{@link Bean}修饰的类注册为元bean，并装载到{@code metaBeanMap}容器中。<br/>
     * <p/>
     * 当Bean注解上{@code value}为空时，将会自动解析获取对应的全限定类名，否则以已有字符串为准。<br/>
     *
     * @param packageName 全限定包名
     * @return {@link Map}<{@link String}, {@link Class}<{@link ?}>> 返回一个包含对应元bean实例的容器.
     * K为全限定类名，V为元bean
     */
    public static Map<String, MetaBean> registerMetaBeanFromAnnotation(String packageName) {
        // 接收元bean信息
        Map<String, MetaBean> metaBeanMap = new HashMap<>(1024);

        // 获取class文件
        List<File> classFiles = RegisterBeanManager.getBeanClassFileFromPackage(packageName);

        // 得到元bean，并加入到metaBeanMap容器中
        for (File file : classFiles) {

            // 接收可能为bean实例的class对象
            Class<?> perhapsBean = loadPerhapsBeanClass(file, packageName);

            // 判断是否是bean组件,是bean则加入IoC容器中进行管理
            isBeanComponent(perhapsBean, metaBeanMap);

        }

        // 返回元BeanMap
        return metaBeanMap;
    }

    /**
     * 扫描加载的可能是bean实例的类
     *
     * @param file        文件
     * @param packageName 包名
     * @return {@link Class}<{@link ?}>
     */
    private static Class<?> loadPerhapsBeanClass(File file, String packageName) {
        // 拼接全限定类名
        String path = file.getPath();
        int begin = path.indexOf(packageName.replace('.', '\\'));
        String className = path.substring(begin, path.length() - 6).replace('\\', '.');

        try {
            // 加载类，获取class对象
            return CLASS_LOADER.loadClass(className);

        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "LoadClassFail", e);
            throw new ReflectionException("LoadClassFail", e);
        }
    }

    /**
     * 如果是bean组件，则加入IoC容器中进行管理。否则不做任何处理。<br/>
     *
     * @param perhapsBean bean实例的class对象
     * @param metaBeanMap bean实例的元信息映射集合
     */
    private static void isBeanComponent(Class<?> perhapsBean, Map<String, MetaBean> metaBeanMap) {
        // 判断是否是bean组件
        boolean beanAnnotationPresent = perhapsBean.isAnnotationPresent(BEAN_ANNOTATION_CLASS);

        // 是bean则加入IoC容器中进行管理
        if (beanAnnotationPresent) {

            Bean beanAnnotation = perhapsBean.getAnnotation(BEAN_ANNOTATION_CLASS);


            // 获取注解上的全限定类名
            String id = beanAnnotation.value();
            // 特判空串时，需要自动获取对应的全限定类名
            if (id.isEmpty()) {
                id = perhapsBean.getName();
            }

            // 注册一个metaBean
            MetaBean metaBean = createMetaBeanWithAnnotation(perhapsBean);

            // 获取注解上的作用域，并设置
            Bean.BeanScope beanScope = beanAnnotation.scope();
            metaBean.setScope(beanScope);

            // 装载到容器中
            metaBeanMap.put(id, metaBean);
        }
    }

    /**
     * 通过bean实例的class对象创建元bean实例。<br/>
     * <p/>
     * 当{@link Reference}注解上{@code value}为空时，将会自动解析获取对应的class对象，否则以已有字符串为准。<br/>
     *
     * @param beanClass bean类
     * @return {@link MetaBean}
     */
    private static MetaBean createMetaBeanWithAnnotation(Class<?> beanClass) {
        Bean beanAnnotation = beanClass.getAnnotation(BEAN_ANNOTATION_CLASS);

        // 获取注解上的全限定类名
        String id = beanAnnotation.value();

        /*开始搜索该类上是否有被其他注解修饰*/
        // 获取bean类的所有字段
        Field[] beanFields = beanClass.getDeclaredFields();
        // 收集注解上的简单值,K为字段名，V为字段值
        Map<String, String> properties = new HashMap<>(64);
        // 收集直接上的引用值，K为字段名，V为引用类型的class对象
        Map<String, Class<?>> references = new HashMap<>(64);

        // 开始遍历搜索注解，并注入收集这些值
        Arrays.stream(beanFields).forEach(field -> {
            // 先获取字段名
            String fieldName = field.getName();

            // 判断
            boolean valueAnnotationPresent = field.isAnnotationPresent(VALUE_ANNOTATION_CLASS);
            if (valueAnnotationPresent) {
                String injectValue = field.getAnnotation(VALUE_ANNOTATION_CLASS).value();
                properties.put(fieldName, injectValue);
            }

            // 判断
            boolean referenceAnnotationPresent = field.isAnnotationPresent(REFERENCE_ANNOTATION_CLASS);
            if (referenceAnnotationPresent) {
                Class<?> injectReference = field.getAnnotation(REFERENCE_ANNOTATION_CLASS).value();

                // 特判为默认值时，需要自动获取全限定类名
                if (injectReference.equals(Class.class)) {
                    injectReference = field.getType();
                }

                // 获取class对象, 并添加到Map中
                references.put(fieldName, injectReference);

            }
        });

        // 注册一个metaBean，并返回该元bean实例
        return new MetaBean(id, beanClass, references, properties);
    }


    /**
     * 从包获取bean类文件
     *
     * @param packageName 包名
     * @return {@link List}<{@link File}>
     */
    private static List<File> getBeanClassFileFromPackage(String packageName) {

        final String suffix = ".class";
        // 获取字节码文件，并返回结果
        return ScannerUtils.getFileFromPackage(packageName, suffix);
    }

    private static final Logger LOGGER =
            Logger.getLogger("com.huanghehua.www.framework.ioc.managet.RegisterBeanManager");
}
