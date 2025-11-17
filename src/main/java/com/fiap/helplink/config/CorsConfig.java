package com.fiap.helplink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                // 🔥 API
                registry.addMapping("/api/**")
                        .allowedOrigins("*")              // pode restringir depois
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowCredentials(false)
                        .maxAge(3600);

                // Swagger UI
                registry.addMapping("/swagger-ui/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET");

                // OpenAPI JSON
                registry.addMapping("/v3/api-docs/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET");
            }
        };
    }
}
