package com.fiap.helplink.dto;

public class AuthResponse {

    private String token;
    private String tipo;
    private Long usuarioId;
    private String email;
    private String nome;
    private String mensagem;

    public AuthResponse() {}

    public AuthResponse(String token, String tipo, Long usuarioId, String email, String nome, String mensagem) {
        this.token = token;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
        this.email = email;
        this.nome = nome;
        this.mensagem = mensagem;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
