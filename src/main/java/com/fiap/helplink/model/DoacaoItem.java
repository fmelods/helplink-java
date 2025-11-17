package com.fiap.helplink.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_DOACAO_ITEM")
public class DoacaoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOACAO_ITEM")
    private Long idDoacaoItem;

    @Column(name = "QTDE", nullable = false)
    private Double qtde = 1.0;

    @Column(name = "DESCRICAO", nullable = false, length = 255)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOACAO", nullable = false)
    private Doacao doacao;

    public DoacaoItem() {}

    public DoacaoItem(Long idDoacaoItem, Double qtde, String descricao, Doacao doacao) {
        this.idDoacaoItem = idDoacaoItem;
        this.qtde = qtde;
        this.descricao = descricao;
        this.doacao = doacao;
    }

    public Long getIdDoacaoItem() { return idDoacaoItem; }
    public void setIdDoacaoItem(Long idDoacaoItem) { this.idDoacaoItem = idDoacaoItem; }

    public Double getQtde() { return qtde; }
    public void setQtde(Double qtde) { this.qtde = qtde; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Doacao getDoacao() { return doacao; }
    public void setDoacao(Doacao doacao) { this.doacao = doacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoacaoItem)) return false;
        DoacaoItem that = (DoacaoItem) o;
        return Objects.equals(idDoacaoItem, that.idDoacaoItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDoacaoItem);
    }
}
