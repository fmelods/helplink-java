package com.fiap.helplink.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HelpLink API")
                        .version("1.0.0")
                        .description("API para plataforma de doações")
                        .contact(new Contact()
                                .name("HelpLink Team")
                                .email("helplink@fiap.com.br")
                                .url("https://github.com/fmelods/helplink")))
                .addSecurityItem(new SecurityRequirement().addList("TOKEN"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("TOKEN",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization") // campo do header
                                        .description("Cole APENAS o token aqui, sem 'Bearer '")
                        ));
    }
}
