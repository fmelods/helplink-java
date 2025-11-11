package com.fiap.helplink.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_HELPLINK_DOACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOACAO")
    private Long idDoacao;

    @Builder.Default
    @Column(name = "STATUS", nullable = false, length = 20)
    private String status = "ABERTA";

    @Builder.Default
    @Column(name = "DT_SOLICITACAO", nullable = false, updatable = false)
    private LocalDateTime dtSolicitacao = LocalDateTime.now();

    @Column(name = "DT_CONFIRMACAO")
    private LocalDateTime dtConfirmacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSTITUICAO", nullable = false)
    private Instituicao instituicao;

    // cascade + orphanRemoval e listas com Builder.Default para nunca virem nulas no builder
    @OneToMany(mappedBy = "doacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<DoacaoItem> doacaoItens = new ArrayList<>();

    @OneToMany(mappedBy = "doacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Agendamento> agendamentos = new ArrayList<>();

    @OneToOne(mappedBy = "doacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Impacto impacto;
}
