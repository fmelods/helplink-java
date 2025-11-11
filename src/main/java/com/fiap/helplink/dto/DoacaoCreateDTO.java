package com.fiap.helplink.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoCreateDTO {
    @NotNull(message = "ID da instituição é obrigatório")
    private Long idInstituicao;

    @NotNull(message = "Lista de itens é obrigatória")
    private List<Long> idItens;
}
