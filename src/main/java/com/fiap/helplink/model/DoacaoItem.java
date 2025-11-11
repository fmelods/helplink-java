package com.fiap.helplink.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_HELPLINK_DOACAO_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoacaoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOACAO_ITEM")
    private Long idDoacaoItem;

    @Builder.Default
    @Column(name = "QTDE", nullable = false)
    private Double qtde = 1.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOACAO", nullable = false)
    private Doacao doacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ITEM", nullable = false)
    private Item item;
}
