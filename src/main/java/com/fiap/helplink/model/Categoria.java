package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_CATEGORIA")
@NoArgsConstructor       // mantém o construtor vazio
@AllArgsConstructor      // mantém o construtor completo
@Builder                // necessário para o DataInitializer
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORIA")
    private Long idCategoria;

    @NotBlank(message = "Nome da categoria é obrigatório")
    @Column(name = "NOME", nullable = false, unique = true, length = 100)
    private String nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Item> itens = new ArrayList<>();

    // ===============================
    // GETTERS & SETTERS
    // ===============================
    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Item> getItens() { return itens; }
    public void setItens(List<Item> itens) { this.itens = itens; }

    // ===============================
    // EQUALS & HASHCODE
    // ===============================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria that = (Categoria) o;
        return Objects.equals(idCategoria, that.idCategoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoria);
    }

    // ===============================
    // TO STRING
    // ===============================
    @Override
    public String toString() {
        return "Categoria{id=" + idCategoria + ", nome='" + nome + "'}";
    }
}
