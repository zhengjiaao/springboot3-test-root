package com.zja.api.openapi.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhengja
 * @Date: 2025-08-04 9:24
 */
@CrossOrigin
@Tag(name = "AdminApi", description = "提供远程-Rest测试接口")
@RestController
@RequestMapping("/api/v1/admin/")
@SecurityRequirement(name = "bearerAuth") // 指定使用的安全方案
public class AdminApiController {

    @GetMapping(value = "/get")
    @Operation(summary = "get-无参数", description = "返回字符串")
    public String get() {
        return "get 请求成功！";
    }

    @GetMapping("/data")
    @PreAuthorize("hasRole('USER')") // 需要 USER 角色
    public ResponseEntity<String> getSecureData() {
        return ResponseEntity.ok("Secure data");
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')") // 需要 ADMIN 角色
    public ResponseEntity<String> adminOperation() {
        return ResponseEntity.ok("Admin operation completed");
    }
}
