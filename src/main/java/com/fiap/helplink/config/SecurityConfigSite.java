package com.fiap.helplink.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@Order(2)
public class SecurityConfigSite {

    @Bean
    public SecurityFilterChain siteSecurity(HttpSecurity http) throws Exception {

        http
                // Tudo que não é API cai aqui
                .securityMatcher("/**")
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Rotas públicas
                        .requestMatchers(
                                "/auth/**",
                                "/",
                                "/index",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico"
                        ).permitAll()

                        // Todas as outras rotas ficam liberadas para o Spring
                        // (mas serão protegidas via HttpSession nos controllers)
                        .anyRequest().permitAll()
                )

                // Spring só cria session quando você chamar HttpSession nos controllers
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                // Desativa login do spring security para usar o seu login manual
                .formLogin(form -> form.disable())

                // Logout controlado pelo seu controller
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
