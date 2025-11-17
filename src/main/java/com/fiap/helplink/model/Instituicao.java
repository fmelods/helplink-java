package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_INSTITUICAO")
@NoArgsConstructor        // necessário para JPA e Builder
@AllArgsConstructor       // necessário para Builder funcionar
@Builder                 // AGORA você pode usar Instituicao.builder()
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSTITUICAO")
    private Long idInstituicao;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "CNPJ é obrigatório")
    @Column(name = "CNPJ", nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(name = "EMAIL", length = 150)
    private String email;

    @Column(name = "CATEGORIAS_ACEITAS", length = 250)
    private String categoriasAceitas;

    @Column(name = "TELEFONE", length = 20)
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private List<Doacao> doacoes = new ArrayList<>();

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private List<Voluntariado> voluntariados = new ArrayList<>();

    // =============================
    // GETTERS & SETTERS
    // =============================

    public Long getIdInstituicao() { return idInstituicao; }
    public void setIdInstituicao(Long idInstituicao) { this.idInstituicao = idInstituicao; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCategoriasAceitas() { return categoriasAceitas; }
    public void setCategoriasAceitas(String categoriasAceitas) { this.categoriasAceitas = categoriasAceitas; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    public List<Doacao> getDoacoes() { return doacoes; }
    public void setDoacoes(List<Doacao> doacoes) { this.doacoes = doacoes; }

    public List<Voluntariado> getVoluntariados() { return voluntariados; }
    public void setVoluntariados(List<Voluntariado> voluntariados) { this.voluntariados = voluntariados; }

    // =============================
    // EQUALS & HASHCODE
    // =============================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instituicao)) return false;
        Instituicao that = (Instituicao) o;
        return Objects.equals(idInstituicao, that.idInstituicao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInstituicao);
    }

    // =============================
    // TO STRING
    // =============================
    @Override
    public String toString() {
        return "Instituicao{id=" + idInstituicao + ", nome='" + nome + "'}";
    }
}
