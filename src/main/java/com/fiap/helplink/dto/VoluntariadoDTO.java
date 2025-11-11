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
public class VoluntariadoDTO {
    private Long idVoluntariado;
    private Integer horasDedicadas;
    private String habilidades;
    private LocalDateTime dtParticipacao;
    private Long usuarioId;
    private String usuarioNome;
    private Long instituicaoId;
    private String instituicaoNome;
}
