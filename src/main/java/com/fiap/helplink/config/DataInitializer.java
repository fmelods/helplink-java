package com.fiap.helplink.config;

import com.fiap.helplink.model.*;
import com.fiap.helplink.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("!prod")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // ===========================
        // CATEGORIAS
        // ===========================
        String[] nomesCategorias = {
                "Roupas", "Alimentos", "Livros", "Brinquedos",
                "Eletrônicos", "Móveis", "Higiene", "Medicamentos",
                "Utensílios", "Outros"
        };

        for (String nome : nomesCategorias) {
            if (!categoriaRepository.existsByNome(nome)) {
                categoriaRepository.save(Categoria.builder().nome(nome).build());
            }
        }

        // ===========================
        // USUÁRIOS DE TESTE
        // ===========================
        if (usuarioRepository.findAll().isEmpty()) {
            Usuario user1 = Usuario.builder()
                    .nome("João Silva")
                    .email("joao@helplink.com")
                    .senha(passwordEncoder.encode("senha123"))
                    .telefone("(11) 98765-4321")
                    .build();

            Usuario user2 = Usuario.builder()
                    .nome("Maria Santos")
                    .email("maria@helplink.com")
                    .senha(passwordEncoder.encode("senha123"))
                    .telefone("(11) 99876-5432")
                    .build();

            usuarioRepository.saveAll(Arrays.asList(user1, user2));
        }

        // ===========================
        // INSTITUIÇÕES
        // ===========================
        if (instituicaoRepository.findAll().isEmpty()) {
            Instituicao inst1 = Instituicao.builder()
                    .nome("ONG Solidária São Paulo")
                    .cnpj("12.345.678/0001-99")
                    .email("contato@ongsolidaria.org.br")
                    .categoriasAceitas("Roupas, Alimentos, Livros, Brinquedos")
                    .telefone("(11) 3456-7890")
                    .build();

            Instituicao inst2 = Instituicao.builder()
                    .nome("Abrigo Nossa Senhora da Esperança")
                    .cnpj("98.765.432/0001-11")
                    .email("abrigo@esperanca.org.br")
                    .categoriasAceitas("Alimentos, Roupas, Higiene, Utensílios")
                    .telefone("(11) 2345-6789")
                    .build();

            instituicaoRepository.saveAll(Arrays.asList(inst1, inst2));
        }

        // ===========================
        // LOG
        // ===========================
        System.out.println("✓ Dados de inicialização carregados com sucesso!");
        System.out.println("✓ Usuário de teste: admin@ex.com / fiap25");
        System.out.println("✓ Swagger disponível em: http://localhost:8080/swagger-ui/index.html");
    }
}
