package com.fiap.helplink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String refreshToken;
    private String tipo;
    private Long usuarioId;
    private String email;
    private String nome;
    private String mensagem;
}
