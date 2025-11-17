package com.fiap.helplink.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_IMPACTO")
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

    // CONSTRUTORES
    public Impacto() {}

    public Impacto(Long idImpacto, Double pontuacao, String observacao, Doacao doacao) {
        this.idImpacto = idImpacto;
        this.pontuacao = pontuacao;
        this.observacao = observacao;
        this.doacao = doacao;
    }

    // GETTERS & SETTERS
    public Long getIdImpacto() { return idImpacto; }
    public void setIdImpacto(Long idImpacto) { this.idImpacto = idImpacto; }

    public Double getPontuacao() { return pontuacao; }
    public void setPontuacao(Double pontuacao) { this.pontuacao = pontuacao; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Doacao getDoacao() { return doacao; }
    public void setDoacao(Doacao doacao) { this.doacao = doacao; }

    // EQUALS & HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Impacto)) return false;
        Impacto impacto = (Impacto) o;
        return Objects.equals(idImpacto, impacto.idImpacto);
    }

    @Override
    public int hashCode() { return Objects.hash(idImpacto); }

    // TO STRING
    @Override
    public String toString() {
        return "Impacto{id=" + idImpacto + ", pontuacao=" + pontuacao + "}";
    }
}
