package com.zja.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhengja
 * @Date: 2025-07-30 15:58
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API 文档",
                version = "1.0",
                description = "Spring Boot 3 项目文档"
        )
)
public class OpenAPIConfig {
    // 可添加更多自定义配置

/*    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                // 与上面注解实现一致
                // .info(new Info()
                //         .title("API 接口文档")
                //         .version("1.0.0")
                //         .description("Spring Boot 3 集成 SpringDoc 示例项目")
                //         .contact(new Contact()
                //                 .name("开发团队")
                //                 .email("dev@example.com")
                //                 .url("https://example.com")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }*/
}
