/**
 * @Company: 上海数慧系统技术有限公司
 * @Department: 数据中心
 * @Author: 郑家骜[ào]
 * @Email: zhengja@dist.com.cn
 * @Date: 2023-11-02 16:46
 * @Since:
 */
package com.zja.tika;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author: zhengja
 * @since: 2023/11/02 16:46
 */
public class OfficeContentExtractor {


    @Test
    public void testExtractDocxMetadata() throws Exception {
        // 创建Tika解析器
        Parser parser = new AutoDetectParser();

        // 创建内容处理器和元数据对象
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();

        // 创建解析上下文
        ParseContext context = new ParseContext();

        // 提取Excel文件内容
        try (InputStream inputStream = Files.newInputStream(Paths.get("D:\\temp\\word\\test.docx"))) {
            parser.parse(inputStream, handler, metadata, context);
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }

        // 获取提取的内容
        String extractedContent = handler.toString();
        System.out.println(extractedContent);
    }

    @Test
    public void testExtractDocxMetadata2() throws Exception {
        String extractedContent = ApacheTikaUtil.extractedContent("D:\\temp\\word\\test.docx");
        System.out.println(extractedContent);
    }

}
