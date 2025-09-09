package com.zja.api.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhengja
 * @Date: 2025-08-04 9:24
 */
@CrossOrigin
@Tag(name = "PublicApi", description = "提供远程-Rest测试接口")
@RestController
@RequestMapping("/api/v1/public/")
public class PublicApiController {

    @GetMapping(value = "/get")
    @Operation(summary = "get-无参数", description = "返回字符串")
    public String get() {
        return "get 请求成功！";
    }
}
