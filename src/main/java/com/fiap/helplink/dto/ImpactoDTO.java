package com.fiap.helplink.dto;

public class ImpactoDTO {

    private Long idImpacto;
    private Double pontuacao;
    private String observacao;
    private Long doacaoId;

    public ImpactoDTO() {}

    public ImpactoDTO(Long idImpacto, Double pontuacao, String observacao, Long doacaoId) {
        this.idImpacto = idImpacto;
        this.pontuacao = pontuacao;
        this.observacao = observacao;
        this.doacaoId = doacaoId;
    }

    public Long getIdImpacto() {
        return idImpacto;
    }

    public void setIdImpacto(Long idImpacto) {
        this.idImpacto = idImpacto;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getDoacaoId() {
        return doacaoId;
    }

    public void setDoacaoId(Long doacaoId) {
        this.doacaoId = doacaoId;
    }
}
