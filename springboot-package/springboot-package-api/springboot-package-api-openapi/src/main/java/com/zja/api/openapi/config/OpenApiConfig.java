package com.zja.api.openapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApi 配置类
 *
 * @Author: zhengja
 * @Date: 2025-07-31 9:49
 */
@Configuration
public class OpenApiConfig {

   // 可选的配置，可自行添加
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("企业级 API 接口文档")
                        .version("2.0.0")
                        .description("Spring Boot 3.2 + SpringDoc 2.8.x 集成示例项目")
                        .termsOfService("https://example.com/terms")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org"))
                        .contact(new Contact()
                                .name("API 支持团队")
                                .email("api-support@example.com")
                                .url("https://example.com/contact")));
    }
}