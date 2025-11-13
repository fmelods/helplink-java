package com.fiap.helplink.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AuthResponse {
    private String token;
    private String tipo;
    private Long usuarioId;
    private String email;
    private String nome;
    private String mensagem;
}
