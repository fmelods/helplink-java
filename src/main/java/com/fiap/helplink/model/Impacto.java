package com.fiap.helplink.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_HELPLINK_IMPACTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Impacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_IMPACTO")
    private Long idImpacto;

    @Column(name = "PONTUACAO")
    private Double pontuacao;

    @Column(name = "OBSERVACAO", length = 250)
    private String observacao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOACAO", nullable = false, unique = true)
    @JsonBackReference
    private Doacao doacao;
}
