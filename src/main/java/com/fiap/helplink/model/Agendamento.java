package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_AGENDAMENTO")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDAMENTO")
    private Long idAgendamento;

    @NotBlank(message = "Tipo de agendamento é obrigatório")
    @Column(name = "TIPO", nullable = false, length = 20)
    private String tipo;

    @Column(name = "DT_AGENDAMENTO", nullable = false)
    private LocalDateTime dtAgendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOACAO", nullable = false)
    private Doacao doacao;

    // =========================
    // CONSTRUTORES
    // =========================
    public Agendamento() {}

    public Agendamento(Long idAgendamento, String tipo, LocalDateTime dtAgendamento, Doacao doacao) {
        this.idAgendamento = idAgendamento;
        this.tipo = tipo;
        this.dtAgendamento = dtAgendamento;
        this.doacao = doacao;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public Long getIdAgendamento() { return idAgendamento; }
    public void setIdAgendamento(Long idAgendamento) { this.idAgendamento = idAgendamento; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDateTime getDtAgendamento() { return dtAgendamento; }
    public void setDtAgendamento(LocalDateTime dtAgendamento) { this.dtAgendamento = dtAgendamento; }

    public Doacao getDoacao() { return doacao; }
    public void setDoacao(Doacao doacao) { this.doacao = doacao; }

    // =========================
    // EQUALS & HASHCODE
    // =========================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agendamento)) return false;
        Agendamento that = (Agendamento) o;
        return Objects.equals(idAgendamento, that.idAgendamento);
    }

    @Override
    public int hashCode() { return Objects.hash(idAgendamento); }

    // =========================
    // TO STRING
    // =========================
    @Override
    public String toString() {
        return "Agendamento{id=" + idAgendamento +
                ", tipo='" + tipo + "', dtAgendamento=" + dtAgendamento + "}";
    }
}
