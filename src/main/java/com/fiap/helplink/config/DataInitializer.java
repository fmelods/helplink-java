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

        // ============================
        // CATEGORIAS
        // ============================
        String[] nomesCategorias = {
                "Roupas", "Alimentos", "Livros", "Brinquedos",
                "Eletrônicos", "Móveis", "Higiene", "Medicamentos",
                "Utensílios", "Outros"
        };

        for (String nome : nomesCategorias) {
            if (!categoriaRepository.existsByNome(nome)) {
                Categoria c = new Categoria();
                c.setNome(nome);
                categoriaRepository.save(c);
            }
        }

        // ============================
        // USUÁRIOS DE TESTE
        // ============================
        if (usuarioRepository.findAll().isEmpty()) {

            Usuario u1 = new Usuario();
            u1.setNome("João Silva");
            u1.setEmail("joao@helplink.com");
            u1.setSenha(passwordEncoder.encode("senha123"));
            u1.setTelefone("(11) 98765-4321");

            Usuario u2 = new Usuario();
            u2.setNome("Maria Santos");
            u2.setEmail("maria@helplink.com");
            u2.setSenha(passwordEncoder.encode("senha123"));
            u2.setTelefone("(11) 99876-5432");

            usuarioRepository.saveAll(Arrays.asList(u1, u2));
        }

        // ============================
        // USUÁRIO ADMIN DO LOGIN API
        // ============================
        if (!usuarioRepository.existsByEmail("admin@ex.com")) {

            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@ex.com");
            admin.setSenha(passwordEncoder.encode("fiap25")); // SENHA CERTA AQUI
            admin.setTelefone("(11) 99999-9999");

            usuarioRepository.save(admin);

            System.out.println("✓ Usuário admin criado.");
        } else {
            System.out.println("✓ Usuário admin já existe.");
        }

        // ============================
        // INSTITUIÇÕES
        // ============================
        if (instituicaoRepository.findAll().isEmpty()) {

            Instituicao inst1 = new Instituicao();
            inst1.setNome("ONG Solidária São Paulo");
            inst1.setCnpj("12.345.678/0001-99");
            inst1.setEmail("contato@ongsolidaria.org.br");
            inst1.setCategoriasAceitas("Roupas, Alimentos, Livros, Brinquedos");
            inst1.setTelefone("(11) 3456-7890");

            Instituicao inst2 = new Instituicao();
            inst2.setNome("Abrigo Nossa Senhora da Esperança");
            inst2.setCnpj("98.765.432/0001-11");
            inst2.setEmail("abrigo@esperanca.org.br");
            inst2.setCategoriasAceitas("Alimentos, Roupas, Higiene, Utensílios");
            inst2.setTelefone("(11) 2345-6789");

            instituicaoRepository.saveAll(Arrays.asList(inst1, inst2));
        }

        System.out.println("==============================================");
        System.out.println("✓ Dados carregados com sucesso!");
        System.out.println("✓ Login API → admin@ex.com / fiap25");
        System.out.println("✓ Site disponível em → http://localhost:8080");
        System.out.println("==============================================");
    }
}
