package com.fiap.helplink.dto;

import java.time.LocalDate;

public class VoluntariadoDTO {

    private Long idVoluntariado;
    private Integer horasDedicadas;
    private String habilidades;
    private LocalDate dtParticipacao;
    private Long usuarioId;
    private String usuarioNome;
    private Long instituicaoId;
    private String instituicaoNome;

    public VoluntariadoDTO() {}

    public VoluntariadoDTO(Long idVoluntariado, Integer horasDedicadas, String habilidades,
                           LocalDate dtParticipacao, Long usuarioId,
                           String usuarioNome, Long instituicaoId, String instituicaoNome) {
        this.idVoluntariado = idVoluntariado;
        this.horasDedicadas = horasDedicadas;
        this.habilidades = habilidades;
        this.dtParticipacao = dtParticipacao;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.instituicaoId = instituicaoId;
        this.instituicaoNome = instituicaoNome;
    }

    public Long getIdVoluntariado() { return idVoluntariado; }
    public void setIdVoluntariado(Long idVoluntariado) { this.idVoluntariado = idVoluntariado; }

    public Integer getHorasDedicadas() { return horasDedicadas; }
    public void setHorasDedicadas(Integer horasDedicadas) { this.horasDedicadas = horasDedicadas; }

    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }

    public LocalDate getDtParticipacao() { return dtParticipacao; }
    public void setDtParticipacao(LocalDate dtParticipacao) { this.dtParticipacao = dtParticipacao; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getUsuarioNome() { return usuarioNome; }
    public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }

    public Long getInstituicaoId() { return instituicaoId; }
    public void setInstituicaoId(Long instituicaoId) { this.instituicaoId = instituicaoId; }

    public String getInstituicaoNome() { return instituicaoNome; }
    public void setInstituicaoNome(String instituicaoNome) { this.instituicaoNome = instituicaoNome; }
}
