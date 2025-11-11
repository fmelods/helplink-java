package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_HELPLINK_INSTITUICAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSTITUICAO")
    private Long idInstituicao;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "CNPJ é obrigatório")
    @Column(name = "CNPJ", nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(name = "EMAIL", length = 150)
    private String email;

    @Column(name = "CATEGORIAS_ACEITAS", length = 250)
    private String categoriasAceitas;

    @Column(name = "TELEFONE", length = 20)
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private List<Doacao> doacoes = new ArrayList<>();

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL)
    private List<Voluntariado> voluntariados = new ArrayList<>();
}
