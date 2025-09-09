package com.zja.api.openapi.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhengja
 * @Date: 2025-08-04 13:12
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "安全接口", description = "需要认证的接口")
public class SecureController {

    @GetMapping("/secure-data")
    @Operation(summary = "获取安全数据", description = "需要 JWT 认证")
    @SecurityRequirement(name = "bearerAuth") // 明确指定使用 bearerAuth
    public ResponseEntity<String> getSecureData() {
        return ResponseEntity.ok("This is secure data");
    }

    @GetMapping("/basic-secure")
    @Operation(summary = "基础认证接口", description = "需要 Basic Auth 认证")
    @SecurityRequirement(name = "basicAuth") // 明确指定使用 basicAuth
    public ResponseEntity<String> getBasicSecureData() {
        return ResponseEntity.ok("Basic auth secure data");
    }

    @GetMapping("/public-data")
    @Operation(summary = "公开数据", description = "无需认证")
    @SecurityRequirements() // 明确表示不需要认证
    public ResponseEntity<String> getPublicData() {
        return ResponseEntity.ok("This is public data");
    }
}