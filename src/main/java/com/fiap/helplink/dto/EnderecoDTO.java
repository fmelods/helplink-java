package com.fiap.helplink.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
