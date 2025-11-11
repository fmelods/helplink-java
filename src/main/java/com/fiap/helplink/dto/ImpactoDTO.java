package com.fiap.helplink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpactoDTO {
    private Long idImpacto;
    private Double pontuacao;
    private String observacao;
    private Long doacaoId;
}
