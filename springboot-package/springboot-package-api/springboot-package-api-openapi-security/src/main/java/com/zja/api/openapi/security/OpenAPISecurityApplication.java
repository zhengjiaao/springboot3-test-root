package com.zja.api.openapi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 启动类
 *
 * @swagger: <a href="http://localhost:8080/swagger-ui/index.html">...</a>
 * @author: zhengja
 * @since: 2025/07/31 10:47
 */
@SpringBootApplication
public class OpenAPISecurityApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OpenAPISecurityApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OpenAPISecurityApplication.class);
    }
}