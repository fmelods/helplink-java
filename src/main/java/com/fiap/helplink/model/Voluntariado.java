package com.fiap.helplink.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_HELPLINK_VOLUNTARIADO")
public class Voluntariado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VOLUNTARIADO")
    private Long idVoluntariado;

    @Column(name = "HORAS_DEDICADAS", nullable = false)
    private Integer horasDedicadas;

    @Column(name = "HABILIDADES", length = 100)
    private String habilidades;

    @Column(name = "DT_PARTICIPACAO", nullable = false, updatable = false)
    private LocalDateTime dtParticipacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSTITUICAO", nullable = false)
    private Instituicao instituicao;

    // CONSTRUTORES
    public Voluntariado() {}

    public Voluntariado(Long idVoluntariado, Integer horasDedicadas, String habilidades,
                        LocalDateTime dtParticipacao, Usuario usuario, Instituicao instituicao) {
        this.idVoluntariado = idVoluntariado;
        this.horasDedicadas = horasDedicadas;
        this.habilidades = habilidades;
        this.dtParticipacao = dtParticipacao;
        this.usuario = usuario;
        this.instituicao = instituicao;
    }

    // GETTERS & SETTERS
    public Long getIdVoluntariado() { return idVoluntariado; }
    public void setIdVoluntariado(Long idVoluntariado) { this.idVoluntariado = idVoluntariado; }

    public Integer getHorasDedicadas() { return horasDedicadas; }
    public void setHorasDedicadas(Integer horasDedicadas) { this.horasDedicadas = horasDedicadas; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public LocalDateTime getDtParticipacao() { return dtParticipacao; }
    public void setDtParticipacao(LocalDateTime dtParticipacao) { this.dtParticipacao = dtParticipacao; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Instituicao getInstituicao() { return instituicao; }
    public void setInstituicao(Instituicao instituicao) { this.instituicao = instituicao; }

    // EQUALS & HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Voluntariado that)) return false;
        return Objects.equals(idVoluntariado, that.idVoluntariado);
    }

    @Override
    public int hashCode() { return Objects.hash(idVoluntariado); }

    @Override
    public String toString() {
        return "Voluntariado{idVoluntariado=" + idVoluntariado +
                ", horasDedicadas=" + horasDedicadas + "}";
    }
}
