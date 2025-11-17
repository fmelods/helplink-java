package com.fiap.helplink.dto;

public class EnderecoDTO {

    private Long idEndereco;
    private String logradouro;
    private String numero;
    private String cep;
    private String complemento;
    private String bairroNome;
    private String cidadeNome;
    private String estadoNome;
    private String paisNome;

    public EnderecoDTO() {}

    public EnderecoDTO(Long idEndereco, String logradouro, String numero, String cep,
                       String complemento, String bairroNome, String cidadeNome,
                       String estadoNome, String paisNome) {
        this.idEndereco = idEndereco;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.complemento = complemento;
        this.bairroNome = bairroNome;
        this.cidadeNome = cidadeNome;
        this.estadoNome = estadoNome;
        this.paisNome = paisNome;
    }

    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairroNome() {
        return bairroNome;
    }

    public void setBairroNome(String bairroNome) {
        this.bairroNome = bairroNome;
    }

    public String getCidadeNome() {
        return cidadeNome;
    }

    public void setCidadeNome(String cidadeNome) {
        this.cidadeNome = cidadeNome;
    }

    public String getEstadoNome() {
        return estadoNome;
    }

    public void setEstadoNome(String estadoNome) {
        this.estadoNome = estadoNome;
    }

    public String getPaisNome() {
        return paisNome;
    }

    public void setPaisNome(String paisNome) {
        this.paisNome = paisNome;
    }
}
