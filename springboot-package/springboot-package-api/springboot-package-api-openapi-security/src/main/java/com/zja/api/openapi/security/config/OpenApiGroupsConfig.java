package com.zja.api.openapi.security.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApi 分组配置类（多 API 分组）
 *
 * @Author: zhengja
 * @Date: 2025-07-31 9:50
 */
@Configuration
public class OpenApiGroupsConfig {

    // 以下都是可选的

    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder()
                .group("default-api")
                .pathsToMatch("/**")
                .displayName("1. 默认接口") // 分组显示名称,支持按名称排序
                .build();
    }

    @Bean
    public GroupedOpenApi v1Api() {
        return GroupedOpenApi.builder()
                .group("v1-api")
                .pathsToMatch("/api/v1/**")
                .displayName("2. V1版本接口")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/api/v1/public/**")
                .displayName("3. 公共接口")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-api")
                .pathsToMatch("/api/v1/admin/**")
                .displayName("4. 管理接口")
                .build();
    }

    @Bean
    public GroupedOpenApi internalApi() {
        return GroupedOpenApi.builder()
                .group("internal-api")
                .pathsToMatch("/api/v1/internal/**")
                .displayName("5. 内部接口")
                .build();
    }
}