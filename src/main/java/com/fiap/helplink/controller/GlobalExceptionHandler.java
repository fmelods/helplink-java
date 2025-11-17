package com.fiap.helplink.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.JwtException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==========================================================
    // 404 – ENTIDADE NÃO ENCONTRADA
    // ==========================================================
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", "Recurso não encontrado");
        error.put("detalhes", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // ==========================================================
    // 400 – ERRO DE REGRA DE NEGÓCIO
    // ==========================================================
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", "Requisição inválida");
        error.put("detalhes", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // ==========================================================
    // 400 – ERROS DE VALIDAÇÃO
    // ==========================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        body.put("erro", "Campos inválidos");
        body.put("detalhes", fieldErrors);
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(body);
    }

    // ==========================================================
    // 401 – LOGIN INVÁLIDO
    // ==========================================================
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", "Credenciais inválidas");
        error.put("mensagem", "Email ou senha incorretos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // ==========================================================
    // 401 – ERRO DE AUTENTICAÇÃO (JWT)
    // ==========================================================
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthentication(AuthenticationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", "Falha de autenticação");
        error.put("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // ==========================================================
    // 401 – TOKEN EXPIRADO / JWT INVÁLIDO
    // ==========================================================
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> handleJwt(JwtException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("erro", "Token JWT inválido");
        error.put("mensagem", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // ==========================================================
    // 500 – ERROS INESPERADOS (FALHA NO SERVIDOR)
    // ==========================================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {

        Map<String, String> error = new HashMap<>();
        error.put("erro", "Erro interno do servidor");
        error.put("detalhes", ex.getMessage());

        ex.printStackTrace(); // Log

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
