package com.fiap.helplink.dto;

import java.time.LocalDateTime;

public class InstituicaoDTO {

    private Long idInstituicao;
    private String nome;
    private String cnpj;
    private String email;
    private String categoriasAceitas;
    private String telefone;
    private LocalDateTime dtCadastro;
    private EnderecoDTO endereco;

    public InstituicaoDTO() {}

    public InstituicaoDTO(Long idInstituicao, String nome, String cnpj, String email,
                          String categoriasAceitas, String telefone,
                          LocalDateTime dtCadastro, EnderecoDTO endereco) {
        this.idInstituicao = idInstituicao;
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.categoriasAceitas = categoriasAceitas;
        this.telefone = telefone;
        this.dtCadastro = dtCadastro;
        this.endereco = endereco;
    }

    public Long getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Long idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategoriasAceitas() {
        return categoriasAceitas;
    }

    public void setCategoriasAceitas(String categoriasAceitas) {
        this.categoriasAceitas = categoriasAceitas;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}
