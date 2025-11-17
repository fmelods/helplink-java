package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_ESTADO")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;

    @NotBlank(message = "Nome do estado é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAIS")
    private Pais pais;

    // CONSTRUTORES
    public Estado() {}

    public Estado(Long idEstado, String nome, Pais pais) {
        this.idEstado = idEstado;
        this.nome = nome;
        this.pais = pais;
    }

    // GETTERS & SETTERS
    public Long getIdEstado() { return idEstado; }
    public void setIdEstado(Long idEstado) { this.idEstado = idEstado; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }

    // EQUALS & HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estado)) return false;
        Estado estado = (Estado) o;
        return Objects.equals(idEstado, estado.idEstado);
    }

    @Override
    public int hashCode() { return Objects.hash(idEstado); }

    // TO STRING
    @Override
    public String toString() {
        return "Estado{id=" + idEstado + ", nome='" + nome + "'}";
    }
}
