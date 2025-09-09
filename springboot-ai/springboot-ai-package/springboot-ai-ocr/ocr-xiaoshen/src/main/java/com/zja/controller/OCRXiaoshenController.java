/**
 * @Company: 上海数慧系统技术有限公司
 * @Department: 数据中心
 * @Author: 郑家骜[ào]
 * @Email: zhengja@dist.com.cn
 * @Date: 2023-11-08 10:34
 * @Since:
 */
package com.zja.controller;

import com.zja.service.OCRXiaoshenService;
import com.zja.util.AsposePdfUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * OCR服务
 *
 * @author: zhengja
 * @since: 2023/11/08 10:34
 */
@Validated
@RestController
@RequestMapping("/rest/ocr/")
@Tag(name = "OCR服务页面")
public class OCRXiaoshenController {

    @Autowired
    OCRXiaoshenService service;

    @Value("${storage-dir}")
    public String storageDir;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public String upload(@Parameter(description = "上传文件（可以从多种不同的文件类型（例如 PDF文件、Office文件、图像文件、XLS 和 超文本标记语言文件等）中检测并提取元数据和文本）") @RequestPart(value = "file") MultipartFile file) throws IOException {

        if (file.isEmpty() || file.getSize() <= 0) {
            return "未选择要上传的文件！";
        }

        System.out.println("上传文件：" + file.getOriginalFilename());

        String fileId = UUID.randomUUID().toString();
        String filePath = storageDir + File.separator + fileId + File.separator + file.getOriginalFilename();

        File tmpFile = new File(filePath);
        if (!tmpFile.getParentFile().exists()) {
            tmpFile.getParentFile().mkdirs();
        }

        // 存储到本地临时
        file.transferTo(new File(filePath));

        return fileId;
    }

    @GetMapping("/general")
    @Operation(summary = "OCR自动识别文件格式进行提取文本内容", description = "已启用提取pdf、word等中图片文本内容")
    public void autoExtractContent(@RequestParam String fileId) {

        String fileParentPath = storageDir + File.separator + fileId;
        File firstFile = getFirstFile(fileParentPath);

        String content = service.autoExtractContent(firstFile.getAbsolutePath());
        storeResultFile(fileId, content);
    }

    @GetMapping("/accurate_basic")
    @Operation(summary = "OCR-图像高精度基础版", description = "仅支持提取图像和PDF内容")
    public void accurateBasic(@RequestParam String fileId,
                              @RequestParam(required = false) Integer pageNum) {

        String fileParentPath = storageDir + File.separator + fileId;
        File firstFile = getFirstFile(fileParentPath);

        String content = service.accurateBasic(firstFile.getAbsolutePath(), pageNum);
        storeResultFile(fileId, content);
    }

    @GetMapping("/accurate_position")
    @Operation(summary = "OCR-图像高精度含位置版", description = "仅支持提取图像和PDF内容")
    public void accuratePosition(@RequestParam String fileId,
                                 @RequestParam(required = false) Integer pageNum) {

        String fileParentPath = storageDir + File.separator + fileId;
        File firstFile = getFirstFile(fileParentPath);

        String content = service.accuratePosition(firstFile.getAbsolutePath(), pageNum);
        storeResultFile(fileId, content);
    }

    @GetMapping("/download/result")
    @Operation(summary = "下载OCR提取文本的结果")
    public void downloadResult(HttpServletResponse response,
                               @RequestParam String fileId) throws IOException {
        File resultFile = getResultFile(fileId);

        FileInputStream inputStream = FileUtils.openInputStream(resultFile);
        byte[] bytes = toByteArray(inputStream);
        inputStream.close();
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode("result.txt", "UTF-8"));
        response.setContentLength(bytes.length);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.close();
        inputStream.close();
    }

    @GetMapping("/to_pdf")
    @Operation(summary = "转为pdf", description = "仅支持Office格式文件")
    public String toPdf(@RequestParam String fileId) throws Exception {

        String fileParentPath = storageDir + File.separator + fileId;
        File firstFile = getFirstFile(fileParentPath);

        String pdfFileId = UUID.randomUUID().toString();
        String pdfFilePath = storageDir + File.separator + pdfFileId + File.separator + "to.pdf";
        File tmpFile = new File(pdfFilePath);
        if (!tmpFile.getParentFile().exists()) {
            tmpFile.getParentFile().mkdirs();
        }

        // 转为pdf
        AsposePdfUtil.officeToPdf(firstFile.getAbsolutePath(), pdfFilePath);

        return pdfFileId;
    }

    @GetMapping("/pages_count")
    @Operation(summary = "获取文件总页数", description = "仅支持PDF")
    public int pagesCount(@RequestParam String fileId) throws IOException {

        String fileParentPath = storageDir + File.separator + fileId;
        File firstFile = getFirstFile(fileParentPath);

        return service.getPdfPagesCount(firstFile.getAbsolutePath());
    }

    private File getFirstFile(String fileParentPath) {
        File tmpFileDir = new File(fileParentPath);
        if (!tmpFileDir.exists()) {
            throw new RuntimeException("文件未上传.");
        }

        File[] files = tmpFileDir.listFiles();
        if (null == files) {
            throw new RuntimeException("文件未上传.");
        }

        for (File file : files) {
            if (file.isFile()) {
                return file;
            }
        }

        throw new RuntimeException("文件未上传.");
    }


    private void storeResultFile(String fileId, String data) {
        String resultFilePath = storageDir + File.separator + fileId + File.separator + "result" + File.separator + "result.txt";
        File resultFile = new File(resultFilePath);
        if (!resultFile.getParentFile().exists()) {
            resultFile.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFilePath))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getResultFile(String fileId) {
        String resultFilePath = storageDir + File.separator + fileId + File.separator + "result" + File.separator + "result.txt";
        File resultFile = new File(resultFilePath);
        if (!resultFile.exists()) {
            throw new RuntimeException("不存在结果文件.");
        }

        return resultFile;
    }

    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}