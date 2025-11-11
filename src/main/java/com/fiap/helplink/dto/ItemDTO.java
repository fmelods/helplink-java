package com.fiap.helplink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
