package com.huanghehua.www.dispatch.util;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * json解析工具类
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/30
 */
public class JsonUtils {
    private static String getJsonString(HttpServletRequest request) throws IOException {
        BufferedReader requestBody = request.getReader();
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = requestBody.readLine()) != null) {
            builder.append(line);
        }
        requestBody.close();
        return builder.toString();
    }
    public static <T> T parseJson(HttpServletRequest request, Class<T> beanClass) throws IOException {
        String jsonString = getJsonString(request);
        return JSON.parseObject(jsonString, beanClass);
    }
    public static String toJsonString(Object bean) {
        return JSON.toJSONString(bean);
    }
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.dispatch.com.huanghehua.www.dispatch.util.JsonUtils");
}
