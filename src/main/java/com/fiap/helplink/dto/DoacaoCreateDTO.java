package com.fiap.helplink.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DoacaoCreateDTO {

    @NotNull(message = "ID da instituição é obrigatório")
    private Long idInstituicao;

    @NotBlank(message = "A descrição do item é obrigatória")
    private String itemDescricao;

    public DoacaoCreateDTO() {}

    public DoacaoCreateDTO(Long idInstituicao, String itemDescricao) {
        this.idInstituicao = idInstituicao;
        this.itemDescricao = itemDescricao;
    }

    public Long getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Long idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getItemDescricao() {
        return itemDescricao;
    }

    public void setItemDescricao(String itemDescricao) {
        this.itemDescricao = itemDescricao;
    }
}
