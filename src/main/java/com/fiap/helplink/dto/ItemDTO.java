package com.fiap.helplink.dto;

import java.time.LocalDateTime;

public class ItemDTO {

    private Long idItem;
    private String titulo;
    private String fotoUrl;
    private String estadoConservacao;
    private String descricao;
    private LocalDateTime dtRegistro;
    private Long usuarioId;
    private String usuarioNome;
    private Long categoriaId;
    private String categoriaNome;

    public ItemDTO() {}

    public ItemDTO(Long idItem, String titulo, String fotoUrl, String estadoConservacao,
                   String descricao, LocalDateTime dtRegistro, Long usuarioId,
                   String usuarioNome, Long categoriaId, String categoriaNome) {

        this.idItem = idItem;
        this.titulo = titulo;
        this.fotoUrl = fotoUrl;
        this.estadoConservacao = estadoConservacao;
        this.descricao = descricao;
        this.dtRegistro = dtRegistro;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
    }

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDtRegistro() {
        return dtRegistro;
    }

    public void setDtRegistro(LocalDateTime dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }
}
