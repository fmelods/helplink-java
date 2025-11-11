package com.fiap.helplink.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_HELPLINK_VOLUNTARIADO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voluntariado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VOLUNTARIADO")
    private Long idVoluntariado;

    @Column(name = "HORAS_DEDICADAS", nullable = false)
    private Integer horasDedicadas;

    @Column(name = "HABILIDADES", length = 100)
    private String habilidades;

    @Builder.Default
    @Column(name = "DT_PARTICIPACAO", nullable = false, updatable = false)
    private LocalDateTime dtParticipacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSTITUICAO", nullable = false)
    private Instituicao instituicao;
}
