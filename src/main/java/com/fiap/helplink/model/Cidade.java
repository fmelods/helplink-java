package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_CIDADE")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCidade;

    @NotBlank(message = "Nome da cidade é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO")
    private Estado estado;

    // CONSTRUTORES
    public Cidade() {}

    public Cidade(Long idCidade, String nome, Estado estado) {
        this.idCidade = idCidade;
        this.nome = nome;
        this.estado = estado;
    }

    // GETTERS & SETTERS
    public Long getIdCidade() { return idCidade; }
    public void setIdCidade(Long idCidade) { this.idCidade = idCidade; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    // EQUALS & HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cidade)) return false;
        Cidade cidade = (Cidade) o;
        return Objects.equals(idCidade, cidade.idCidade);
    }

    @Override
    public int hashCode() { return Objects.hash(idCidade); }

    @Override
    public String toString() {
        return "Cidade{id=" + idCidade + ", nome='" + nome + "'}";
    }
}
