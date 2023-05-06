package com.huanghehua.www.framework.ioc.util;

import com.huanghehua.www.ioc.exception.ReflectionException;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 可以调用该类中的静态方法，实现深克隆。<br/>
 * <p/>
 * 需要注意的是，深克隆的对象应该实现了{@link Serializable}接口。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/19
 */
public class PrototypeUtil {

    public static Object deepClone(Object obj) {
        // 接收序列化数据
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // 序列化
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);

            // 得到序列化结果
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            // 反序列化
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Serialization fail", e);
            throw new ReflectionException("Serialization fail", e);
        }
    }

    private static final Logger LOGGER =
            Logger.getLogger("com.huanghehua.www.framework.ioc.com.huanghehua.www.dispatch.util.PrototypeUtil");
}
