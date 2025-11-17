package com.fiap.helplink.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaDTO {

    private Long idCategoria;

    @NotBlank(message = "Nome da categoria é obrigatório")
    private String nome;

    public CategoriaDTO() {}

    public CategoriaDTO(Long idCategoria, String nome) {
        this.idCategoria = idCategoria;
        this.nome = nome;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
