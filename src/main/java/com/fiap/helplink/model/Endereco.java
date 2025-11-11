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
@Table(name = "TB_HELPLINK_ENDERECO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEndereco;

    @NotBlank(message = "Logradouro é obrigatório")
    @Column(nullable = false, length = 100)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 9)
    private String cep;

    @Column(length = 50)
    private String complemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bairro")
    private Bairro bairro;

    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private List<Instituicao> instituicoes = new ArrayList<>();
}
