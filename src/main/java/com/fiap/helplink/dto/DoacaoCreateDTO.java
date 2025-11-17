package com.fiap.helplink.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DoacaoCreateDTO {

    @NotNull(message = "ID da instituição é obrigatório")
    private Long idInstituicao;

    @NotBlank(message = "A descrição do item é obrigatória")
    private String itemDescricao;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    public DoacaoCreateDTO() {}

    public DoacaoCreateDTO(Long idInstituicao, String itemDescricao, String status) {
        this.idInstituicao = idInstituicao;
        this.itemDescricao = itemDescricao;
        this.status = status;
    }

    public Long getIdInstituicao() { return idInstituicao; }
    public void setIdInstituicao(Long idInstituicao) { this.idInstituicao = idInstituicao; }

    public String getItemDescricao() { return itemDescricao; }
    public void setItemDescricao(String itemDescricao) { this.itemDescricao = itemDescricao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
