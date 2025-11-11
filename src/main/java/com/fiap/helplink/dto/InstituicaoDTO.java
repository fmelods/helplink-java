package com.fiap.helplink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstituicaoDTO {
    private Long idInstituicao;
    private String nome;
    private String cnpj;
    private String email;
    private String categoriasAceitas;
    private String telefone;
    private LocalDateTime dtCadastro;
    private EnderecoDTO endereco;
}
