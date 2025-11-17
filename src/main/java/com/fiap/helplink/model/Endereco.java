package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEndereco;

    @NotBlank(message = "Logradouro é obrigatório")
    @Column(nullable = false, length = 100)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 9)
    private String cep;

    @Column(length = 50)
    private String complemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BAIRRO")
    private Bairro bairro;

    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private List<Instituicao> instituicoes = new ArrayList<>();

    // =============================
    // CONSTRUTORES
    // =============================
    public Endereco() {}

    public Endereco(Long idEndereco, String logradouro, String numero, String cep,
                    String complemento, Bairro bairro,
                    List<Usuario> usuarios, List<Instituicao> instituicoes) {

        this.idEndereco = idEndereco;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.complemento = complemento;
        this.bairro = bairro;
        this.usuarios = usuarios;
        this.instituicoes = instituicoes;
    }

    // =============================
    // GETTERS & SETTERS
    // =============================
    public Long getIdEndereco() { return idEndereco; }
    public void setIdEndereco(Long idEndereco) { this.idEndereco = idEndereco; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public Bairro getBairro() { return bairro; }
    public void setBairro(Bairro bairro) { this.bairro = bairro; }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }

    public List<Instituicao> getInstituicoes() { return instituicoes; }
    public void setInstituicoes(List<Instituicao> instituicoes) { this.instituicoes = instituicoes; }

    // =============================
    // EQUALS & HASHCODE
    // =============================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;
        Endereco that = (Endereco) o;
        return Objects.equals(idEndereco, that.idEndereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEndereco);
    }

    // =============================
    // TO STRING
    // =============================
    @Override
    public String toString() {
        return "Endereco{id=" + idEndereco + ", logradouro='" + logradouro + "'}";
    }
}
