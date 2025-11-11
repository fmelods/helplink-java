package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_HELPLINK_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ITEM")
    private Long idItem;

    @NotBlank(message = "Título é obrigatório")
    @Column(name = "TITULO", nullable = false, length = 100)
    private String titulo;

    @Column(name = "FOTO_URL", length = 250)
    private String fotoUrl;

    @Column(name = "ESTADO_CONSERVACAO", length = 50)
    private String estadoConservacao;

    @Column(name = "DESCRICAO", length = 250)
    private String descricao;

    @Builder.Default
    @Column(name = "DT_REGISTRO", nullable = false, updatable = false)
    private LocalDateTime dtRegistro = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOACAO")
    private Doacao doacao;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<DoacaoItem> doacaoItens = new ArrayList<>();
}
