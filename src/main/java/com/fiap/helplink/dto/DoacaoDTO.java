package com.fiap.helplink.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DoacaoDTO {

    private Long idDoacao;
    private String status;
    private LocalDateTime dtSolicitacao;
    private LocalDateTime dtConfirmacao;

    private Long usuarioId;
    private String usuarioNome;

    private Long instituicaoId;
    private String instituicaoNome;

    private String itemDescricao;

    private List<ItemDTO> itens;
    private ImpactoDTO impacto;

    public DoacaoDTO() {}

    public DoacaoDTO(Long idDoacao,
                     String status,
                     LocalDateTime dtSolicitacao,
                     LocalDateTime dtConfirmacao,
                     Long usuarioId,
                     String usuarioNome,
                     Long instituicaoId,
                     String instituicaoNome,
                     String itemDescricao,
                     List<ItemDTO> itens,
                     ImpactoDTO impacto) {

        this.idDoacao = idDoacao;
        this.status = status;
        this.dtSolicitacao = dtSolicitacao;
        this.dtConfirmacao = dtConfirmacao;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.instituicaoId = instituicaoId;
        this.instituicaoNome = instituicaoNome;
        this.itemDescricao = itemDescricao;
        this.itens = itens;
        this.impacto = impacto;
    }

    public Long getIdDoacao() { return idDoacao; }
    public void setIdDoacao(Long idDoacao) { this.idDoacao = idDoacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDtSolicitacao() { return dtSolicitacao; }
    public void setDtSolicitacao(LocalDateTime dtSolicitacao) { this.dtSolicitacao = dtSolicitacao; }

    public LocalDateTime getDtConfirmacao() { return dtConfirmacao; }
    public void setDtConfirmacao(LocalDateTime dtConfirmacao) { this.dtConfirmacao = dtConfirmacao; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuarioNome() { return usuarioNome; }
    public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }

    public Long getInstituicaoId() { return instituicaoId; }
    public void setInstituicaoId(Long instituicaoId) { this.instituicaoId = instituicaoId; }

    public String getInstituicaoNome() { return instituicaoNome; }
    public void setInstituicaoNome(String instituicaoNome) { this.instituicaoNome = instituicaoNome; }

    public String getItemDescricao() { return itemDescricao; }
    public void setItemDescricao(String itemDescricao) { this.itemDescricao = itemDescricao; }

    public List<ItemDTO> getItens() { return itens; }
    public void setItens(List<ItemDTO> itens) { this.itens = itens; }

    public ImpactoDTO getImpacto() { return impacto; }
    public void setImpacto(ImpactoDTO impacto) { this.impacto = impacto; }
}
