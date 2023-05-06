package com.huanghehua.www.orm.util;

import com.huanghehua.www.ioc.exception.ParseException;
import com.huanghehua.www.orm.model.MetaMapperStatement;
import com.huanghehua.www.orm.xml.ParseMapperHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 解析XML文件的工具类。<br/>
 *
 * @author silent_child
 * @version 1.0.0
 * @date 2023/03/14
 */
public class ParseXmlUtils {
    private static final Logger LOGGER = Logger.getLogger("com.huanghehua.www.framework.ParseXmlUtils");
    /**
     * 通过指定包名获取包下所有“.xml”为后缀的文件<br/>
     * <p/>
     * 该方法始终不会返回一个null，当不存在文件时，返回的是一个空的{@link ArrayList}<br/>
     *
     * @param packageName 指定包名，包下含有xml文件
     * @return {@link List}<{@link File}> 返回指定包下的所有xml文件集合
     */
    public static List<File> getXmlFileFromPackage(String packageName) {
        final String suffix = ".xml";

        // 获取xml文件并返回结果
        return com.huanghehua.www.orm.util.ScannerUtils.getFileFromPackage(packageName, suffix);
    }

    /**
     * 通过指定目录路径获取包下所有“.xml”为后缀的文件<br/>
     * <p/>
     * 根据目录名可以获取该目录下的所有xml文件，通过包名可以获取进一步解析子目录。<br/>
     * 该方法始终不会返回一个null，当不存在文件时，返回的是一个空的{@link ArrayList}<br/>
     *
     * @param directoryPath 指定包名的目录路径
     * @return {@link List}<{@link File}> 返回指定目录路径下的所有xml文件集合
     */
    public static List<File> getXmlFileFromDirectory(String directoryPath) {
        final String suffix = ".xml";

        // 获取xml文件，并返回结果
        return ScannerUtils.getFileFromDirectory(directoryPath, suffix);
    }

    /**
     * 解析Mapper.xml文件，用于获取SQL映射对象。<br/>
     * <p/>
     * 根据目录名可以获取该目录下的所有xml文件，通过包名可以获取进一步解析子目录。<br/>
     * 其中核心部分为：
     * 解析处理器{@link ParseMapperHandler}帮助完成了将xml中的sql映射放置到Map集合中。<br/>
     *
     * @param files 文件集合
     * @return {@link Map}<{@link String}, {@link MetaMapperStatement}> K为全限定id，V为SQL映射对象
     */
    public static Map<String, MetaMapperStatement> parseMapper(List<File> files) {
        try {
            // 获取解析器工厂
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            // 获取解析器
            SAXParser saxParser = saxParserFactory.newSAXParser();
            // 获取解析器处理器
            ParseMapperHandler parseMapperHandler = new ParseMapperHandler();

            Map<String, MetaMapperStatement> collect = files.stream()
                    // 将流中的.xml文件进行解析，返回SQL映射集合的K-V条目
                    .map(x -> {
                        try {
                            saxParser.parse(x, parseMapperHandler);
                            // 返回K-V条目回到流中
                            return parseMapperHandler.getStatementMapper().entrySet();
                        } catch (SAXException | IOException e) {
                            LOGGER.log(Level.SEVERE, "解析Mapper.xml文件失败", e);
                            throw new ParseException("解析Mapper.xml文件失败", e);
                        }
                    })
                    // 将Set<Entry<String, String>>一对多映射为Entry<String, String>，即从Set集合中取出元素
                    .flatMap(Set::stream)
                    // 最后通过线程安全的终结管道操作，将流中元素包装进Map集合中进行返回
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (entity1, entity2) -> entity1));

            LOGGER.log(Level.INFO, "解析Mapper.xml文件成功");
            return collect;

        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.log(Level.SEVERE, "解析Mapper.xml文件失败", e);
            throw new ParseException("解析Mapper.xml文件失败", e);
        }
    }
}
