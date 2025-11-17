package com.fiap.helplink.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        String path = request.getRequestURI();
        String errorMsg = authException != null ? authException.getMessage() : "N/A";

        // Log estruturado para auditoria e debugging
        log.warn("⚠ Acesso negado | Rota: {} | Motivo: {}", path, errorMsg);

        // Configura retorno padrão
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        // Header padrão indicando que a rota usa JWT
        response.setHeader("WWW-Authenticate", "Bearer realm=\"helplink-api\"");

        // Construção do JSON de erro
        String json = """
                {
                  "erro": "Acesso não autorizado",
                  "mensagem": "Token JWT ausente, expirado ou inválido.",
                  "rota": "%s",
                  "status": 401
                }
                """.formatted(path);

        try {
            if (!response.isCommitted()) {
                response.getWriter().write(json);
            } else {
                log.error("❌ Resposta já havia sido enviada antes da escrita do JSON.");
            }
        } catch (IllegalStateException e) {
            log.error("❌ Falha ao escrever resposta 401: {}", e.getMessage());
        }
    }
}
