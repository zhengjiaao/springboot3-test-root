package com.zja.aspose.orc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 启动类
 *
 * @swagger: <a href="http://localhost:8080/swagger-ui/index.html">...</a>
 * @author: zhengja
 * @since: 2025/06/30 14:24
 */
@SpringBootApplication
public class AsposeOcrApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AsposeOcrApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AsposeOcrApplication.class);
    }
}