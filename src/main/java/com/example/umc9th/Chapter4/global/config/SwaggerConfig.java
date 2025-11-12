package com.example.umc9th.Chapter4.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info; // 👈 .annotations -> .models.info
import io.swagger.v3.oas.models.security.SecurityRequirement; // 👈 .annotations -> .models.security
import io.swagger.v3.oas.models.security.SecurityScheme; // 👈 .annotations -> .models.security
import io.swagger.v3.oas.models.servers.Server; // 👈 .annotations -> .models.servers

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        Info info = new Info().title("Project").description("Project Swagger").version("0.0.1");

        // JWT 토큰 헤더 방식
        String securityScheme = "JWT TOKEN";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityScheme);

        Components components = new Components()
                .addSecuritySchemes(securityScheme, new SecurityScheme()
                        .name(securityScheme)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/")) // 여기 Server도 수정 필요했습니다!
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}