package com.huanghehua.www.orm.util;

import com.huanghehua.www.ioc.exception.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 扫描工具类，可以返回指定包、目录下指定后缀的所有文件
 *
 * @author timeboy
 * @version 1.0.0
 * @date 2023/03/22
 */
public class ScannerUtils {

    /**
     * 通过指定包名获取包下所有指定后缀的文件<br/>
     * <p/>
     * 该方法始终不会返回一个null，当不存在文件时，返回的是一个空的{@link ArrayList}<br/>
     *
     * @param packageName 指定包名，包下含有指定后缀的文件
     * @param suffix      指定文件的后缀
     * @return {@link List}<{@link File}> 返回指定包下的所有指定后缀的文件集合
     */
    public static List<File> getFileFromPackage(String packageName, String suffix) {
        // 接收文件的容器
        Stream<File> stream = new ArrayList<File>().stream();

        try {
            final String file = "file";
            // 获取指定包下的所有文件的URL
            Enumeration<URL> resources = Thread.currentThread()
                    .getContextClassLoader()
                    // 注意！需要将包名各式转换为目录路径格式
                    .getResources(packageName.replace('.', '/'));

            // 遍历每一个URL
            while (resources.hasMoreElements()) {
                // 获取URL
                URL url = resources.nextElement();
                // 获取协议
                String protocol = url.getProtocol();
                // 如果满足协议，则进行解析，并将解析后的元素合并到集合中
                if (file.equals(protocol)) {
                    stream = Stream.concat(stream,
                            getFileFromDirectory(url.getPath(), suffix).stream());
                }
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "通过包名获取文件失败" + e);
            throw new ParseException("通过包名获取文件失败" + e);
        }

        // 返回结果
        return stream.collect(Collectors.toList());
    }

    /**
     * 通过指定目录路径获取包下所有指定后缀的文件<br/>
     * <p/>
     * 根据目录名可以获取该目录下的所有指定后缀的文件，通过目录名可以获取进一步解析子目录。<br/>
     * 该方法始终不会返回一个null，当不存在文件时，返回的是一个空的{@link ArrayList}<br/>
     *
     * @param directoryPath 指定包名的目录路径
     * @param suffix        指定文件的后缀
     * @return {@link List}<{@link File}> 返回指定目录路径下的指定后缀文件集合
     */
    public static List<File> getFileFromDirectory(String directoryPath, String suffix) {
        // 接收文件的容器
        Stream<File> stream = new ArrayList<File>().stream();
        List<File> list = new ArrayList<>();

        // 根据目录创建文件
        File currentFile = new File(directoryPath);

        // 如果不存在该目录则直接返回一个空list
        if (!currentFile.isDirectory() || !currentFile.exists()) {
            return new ArrayList<>();
        }

        // 获取目录下的所有".xml"结尾的文件和子目录
        File[] files =
                currentFile.listFiles(file -> file.getName().endsWith(suffix) || file.isDirectory());

        // 如果不存在文件，返回一个空list
        if (files == null) {
            return new ArrayList<>();
        }

        for (File file : files) {
            // 如果是的xml文件的话则添加到集合中
            boolean isXml = file.getName().endsWith(suffix);
            if (isXml){
                list.add(file);
            }

            // 如果是子目录则继续搜索
            if (file.isDirectory()) {
                // 并将返回结果与原来的stream进行合并
                stream = Stream.concat(stream,
                        getFileFromDirectory(file.getAbsolutePath(), suffix).stream());
            }

        }
        // 最后再合并一次stream吗，并返回结果
        return Stream.concat(stream, list.stream()).collect(Collectors.toList());
    }

    private static final Logger LOGGER =
            Logger.getLogger("com.huanghehua.www.orm.util.ScannerUtils");
}
