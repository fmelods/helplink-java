package com.fiap.helplink.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                // Informação da API
                .info(new Info()
                        .title("HelpLink API")
                        .description("Documentação da API com autenticação JWT")
                        .version("1.0.0"))

                // Define o requisito de segurança global
                .addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth")
                )

                // Define o esquema JWT
                .components(
                        new Components().addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .name("Authorization")
                                        .description("Insira o token no formato: Bearer {TOKEN}")
                        )
                );
    }
}
