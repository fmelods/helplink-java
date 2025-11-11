package com.fiap.helplink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoDTO {
    private Long idDoacao;
    private String status;
    private LocalDateTime dtSolicitacao;
    private LocalDateTime dtConfirmacao;
    private Long usuarioId;
    private String usuarioNome;
    private Long instituicaoId;
    private String instituicaoNome;
    private List<ItemDTO> itens;   // pode permanecer null se você não mapear aqui
    private ImpactoDTO impacto;    // idem
}
