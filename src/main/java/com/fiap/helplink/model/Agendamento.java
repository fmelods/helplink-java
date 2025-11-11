package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_HELPLINK_AGENDAMENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDAMENTO")
    private Long idAgendamento;

    @NotBlank(message = "Tipo de agendamento é obrigatório")
    @Column(name = "TIPO", nullable = false, length = 20)
    private String tipo;

    @Column(name = "DT_AGENDAMENTO", nullable = false)
    private LocalDateTime dtAgendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOACAO", nullable = false)
    private Doacao doacao;
}
