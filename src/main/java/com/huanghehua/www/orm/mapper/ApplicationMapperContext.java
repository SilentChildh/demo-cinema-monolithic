package com.huanghehua.www.orm.mapper;

import com.huanghehua.www.ioc.spi.orm.MapperFactory;
import com.huanghehua.www.orm.SimpleSqlSession;
import com.huanghehua.www.orm.annotation.GenericKey;
import com.huanghehua.www.orm.annotation.SimpleSql;
import com.huanghehua.www.orm.exception.NotFoundMappingMethodException;
import com.huanghehua.www.orm.exception.ReflectionException;
import com.huanghehua.www.orm.handler.MapperParametersHandler;
import com.huanghehua.www.orm.model.MethodDefinition;
import com.huanghehua.www.orm.model.SqlAnnotationInfo;
import com.huanghehua.www.orm.util.SimpleSqlSessionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO层实现类的代理工厂。<br/>
 * <p/>
 * 可以通过{@code getDAOImplProxy(Class)}方法获取对应的DAO接口实现类。<br/>
 * 对于利用该工厂类获取的实现类，实际上并不会调用实现类的方法，而是调用工厂类的算法。
 * 该工常类需要的是实现类的相关信息，并不需要它们方法体内的算法。<br/>
 * 故实现类中可以不用编写方法体内的内容。</>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/13
 */
public class ApplicationMapperContext implements MapperFactory {

    /**
     * 获取对应DAO接口的实现类的代理类。<br/>
     * <p/>
     * 用户再获取代理类时，需要注意的是用DAO接口进行引用接收代理类。<br/>
     * 传入的参数则是对应DAO接口的class对象。<br/>
     * 需要注意的是，dao层的每一个接口名都应该一<strong>DAO</strong>结尾<br/>
     *
     * @param clazz 对应DAO接口的class对象
     * @return 对应的代理类
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                (proxy, method, args) -> ApplicationMapperContext.invoke(clazz, method, args));
    }

    /**
     * 对数据库进行CRUD操作时，都将会先经过该方法。<br/>
     * <p/>
     * 在调用Object类的方法时，本不会真正调用本类的实例proxy，而是创建一个新的Object实例，以保证运行的安全。<br/>
     *
     * @param interfaceClass dao接口的class对象
     * @param method         方法，即被调用的方法
     * @param args           被调用方法中实参
     * @return {@link Object}
     * @throws SQLException 直接向上抛出
     */
    private static Object invoke(Class<?> interfaceClass, Method method, Object[] args) throws SQLException {
        // 如果调用的不是接口的方法，则抛出异常
        if (method.getDeclaringClass() != interfaceClass) {
            LOGGER.log(Level.SEVERE, "No Such CRUD Method");
            throw new NotFoundMappingMethodException("No Such CRUD Method");
        }


        /*准备会话类相关配置*/
        SimpleSqlSession sqlSession = (SimpleSqlSession) SimpleSqlSessionUtil.openSession();
        // 得到单参数
        MethodDefinition methodDefinition = new MethodDefinition(interfaceClass, method, args);
        Object handle = MapperParametersHandler.handle(methodDefinition);

        // 得到对应class类的相关信息
        String implName = interfaceClass.getName();
        int index = implName.indexOf("Mapper");
        String sqlId = implName.substring(0, index + 6) + '.' + method.getName();
        Class<?> returnType = method.getReturnType();

        // 判断是否注入构建的SQL
        boolean annotationPresent = method.isAnnotationPresent(SimpleSql.class);
        if (annotationPresent) {
            // 获取注解
            SimpleSql annotation = method.getAnnotation(SimpleSql.class);

            // 获取注解上的三个属性
            Class<?> provider = annotation.provider();
            Class<?> resultClass = annotation.resultType();
            Method declaredMethod;
            try {
                declaredMethod = provider.getDeclaredMethod(annotation.method());
                // 得到SQL
                String sql = (String) declaredMethod.invoke(null);

                // 注入注解上的信息
                Class<SimpleSqlSession> simpleSqlSessionClass = SimpleSqlSession.class;
                Field sqlBundle = simpleSqlSessionClass.getDeclaredField(SQL_BUNDLE);

                sqlBundle.setAccessible(true);
                sqlBundle.set(sqlSession, new SqlAnnotationInfo(sql, resultClass));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                LOGGER.log(Level.SEVERE, "反射注入简单自定义构建的SQL语句 fail", e);
                throw new ReflectionException("反射注入简单自定义构建的SQL语句 fail", e);
            }
        }



        /*调用会话类的方法，对数据库执行CRUD操作*/
        if (returnType == int.class) {
            // 判断是否需要返回自增主键
            boolean present = method.isAnnotationPresent(GenericKey.class);
            if (present) {
                return sqlSession.update(sqlId, handle, Statement.RETURN_GENERATED_KEYS);
            }
            else {
                return sqlSession.update(sqlId, handle);
            }
        }
        else if (returnType == List.class) {
            return sqlSession.selectList(sqlId, handle);
        }
        else {
            return sqlSession.selectOne(sqlId, handle);
        }

    }

    private static final String SQL_BUNDLE = "sqlBundle";
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.orm.util.MapperFactory");
}
