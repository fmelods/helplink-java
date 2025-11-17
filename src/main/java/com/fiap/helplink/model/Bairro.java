package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_BAIRRO")
public class Bairro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBairro;

    @NotBlank(message = "Nome do bairro é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CIDADE")
    private Cidade cidade;

    // =========================
    // CONSTRUTORES
    // =========================
    public Bairro() {}

    public Bairro(Long idBairro, String nome, Cidade cidade) {
        this.idBairro = idBairro;
        this.nome = nome;
        this.cidade = cidade;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public Long getIdBairro() { return idBairro; }
    public void setIdBairro(Long idBairro) { this.idBairro = idBairro; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Cidade getCidade() { return cidade; }
    public void setCidade(Cidade cidade) { this.cidade = cidade; }

    // =========================
    // EQUALS & HASHCODE
    // =========================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bairro)) return false;
        Bairro bairro = (Bairro) o;
        return Objects.equals(idBairro, bairro.idBairro);
    }

    @Override
    public int hashCode() { return Objects.hash(idBairro); }

    // =========================
    // TO STRING
    // =========================
    @Override
    public String toString() {
        return "Bairro{id=" + idBairro + ", nome='" + nome + "'}";
    }
}
