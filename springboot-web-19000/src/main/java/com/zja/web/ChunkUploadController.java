package com.zja.web;

import com.zja.service.ChunkUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 这个控制器处理分块文件上传和合并。
 */
@CrossOrigin
@Tag(name = "Chunk Upload")
@RestController
@RequestMapping("/chunk")
public class ChunkUploadController {

    @Autowired
    private ChunkUploadService chunkUploadService;

    /**
     * 上传文件的一个分片
     *
     * @param file        文件分片
     * @param chunkNumber 当前分片编号
     * @param totalChunks 总分片数
     * @param identifier  文件唯一标识
     * @param filename    文件名
     */
    @Operation(summary = "上传文件的一个分片", description = "上传文件的一个分片以便稍后合并")
    @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam("chunkNumber") Integer chunkNumber,
                                    @RequestParam("totalChunks") Integer totalChunks,
                                    @RequestParam("identifier") String identifier,
                                    @RequestParam("filename") String filename) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        chunkUploadService.upload(identifier, file, chunkNumber, totalChunks, filename);
        return ResponseEntity.ok().build();
    }

    /**
     * 合并上传的文件分片
     *
     * @param identifier 文件唯一标识
     * @param filename   文件名
     */
    @Operation(summary = "合并上传的文件分片", description = "将所有上传的文件分片合并成一个文件")
    @PostMapping(name = "/merge")
    public ResponseEntity<?> merge(@RequestParam("identifier") String identifier,
                                   @RequestParam("filename") String filename,
                                   @RequestParam("totalChunks") Integer totalChunks) throws IOException {
        chunkUploadService.merge(identifier, filename, totalChunks);
        return ResponseEntity.ok().build();
    }

}
