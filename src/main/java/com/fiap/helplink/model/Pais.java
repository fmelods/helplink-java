package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_PAIS")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPais;

    @NotBlank(message = "Nome do país é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    // CONSTRUTORES
    public Pais() {}

    public Pais(Long idPais, String nome) {
        this.idPais = idPais;
        this.nome = nome;
    }

    // GETTERS & SETTERS
    public Long getIdPais() { return idPais; }
    public void setIdPais(Long idPais) { this.idPais = idPais; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    // EQUALS & HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pais pais)) return false;
        return Objects.equals(idPais, pais.idPais);
    }

    @Override
    public int hashCode() { return Objects.hash(idPais); }

    // toString
    @Override
    public String toString() {
        return "Pais{idPais=" + idPais + ", nome='" + nome + "'}";
    }
}
