package com.fiap.helplink.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_DOACAO")
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DOACAO")
    private Long idDoacao;

    @Column(name = "STATUS", nullable = false, length = 20)
    private String status = "ABERTA";

    @Column(name = "DT_SOLICITACAO", nullable = false, updatable = false)
    private LocalDateTime dtSolicitacao = LocalDateTime.now();

    @Column(name = "DT_CONFIRMACAO")
    private LocalDateTime dtConfirmacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSTITUICAO", nullable = false)
    private Instituicao instituicao;

    // Lista de itens doados (agora com descrição livre no DoacaoItem)
    @OneToMany(mappedBy = "doacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DoacaoItem> doacaoItens = new ArrayList<>();

    @OneToMany(mappedBy = "doacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos = new ArrayList<>();

    @OneToOne(mappedBy = "doacao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Impacto impacto;

    public Doacao() {}

    public Doacao(Long idDoacao, String status, LocalDateTime dtSolicitacao,
                  LocalDateTime dtConfirmacao, Usuario usuario,
                  Instituicao instituicao) {
        this.idDoacao = idDoacao;
        this.status = status;
        this.dtSolicitacao = dtSolicitacao;
        this.dtConfirmacao = dtConfirmacao;
        this.usuario = usuario;
        this.instituicao = instituicao;
    }

    public Long getIdDoacao() { return idDoacao; }
    public void setIdDoacao(Long idDoacao) { this.idDoacao = idDoacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDtSolicitacao() { return dtSolicitacao; }
    public void setDtSolicitacao(LocalDateTime dtSolicitacao) { this.dtSolicitacao = dtSolicitacao; }

    public LocalDateTime getDtConfirmacao() { return dtConfirmacao; }
    public void setDtConfirmacao(LocalDateTime dtConfirmacao) { this.dtConfirmacao = dtConfirmacao; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Instituicao getInstituicao() { return instituicao; }
    public void setInstituicao(Instituicao instituicao) { this.instituicao = instituicao; }

    public List<DoacaoItem> getDoacaoItens() { return doacaoItens; }
    public void setDoacaoItens(List<DoacaoItem> doacaoItens) { this.doacaoItens = doacaoItens; }

    public List<Agendamento> getAgendamentos() { return agendamentos; }
    public void setAgendamentos(List<Agendamento> agendamentos) { this.agendamentos = agendamentos; }

    public Impacto getImpacto() { return impacto; }
    public void setImpacto(Impacto impacto) { this.impacto = impacto; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doacao)) return false;
        Doacao doacao = (Doacao) o;
        return Objects.equals(idDoacao, doacao.idDoacao);
    }

    @Override
    public int hashCode() { return Objects.hash(idDoacao); }

    @Override
    public String toString() {
        return "Doacao{id=" + idDoacao + ", status='" + status + "'}";
    }
}
