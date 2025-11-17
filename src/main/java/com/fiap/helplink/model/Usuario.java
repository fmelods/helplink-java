package com.fiap.helplink.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "TB_HELPLINK_USUARIO")
public class Usuario implements UserDetails {

    // ============================
    // ATRIBUTOS
    // ============================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(name = "EMAIL", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Column(name = "SENHA", nullable = false, length = 250)
    private String senha;

    @Column(name = "TELEFONE", length = 20)
    private String telefone;

    @Column(name = "DT_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dtCadastro = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Item> itens = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Doacao> doacoes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Voluntariado> voluntariados = new ArrayList<>();


    // ============================
    // CONSTRUTORES
    // ============================

    public Usuario() {}

    public Usuario(Long idUsuario, String nome, String email, String senha,
                   String telefone, LocalDateTime dtCadastro, Endereco endereco) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.dtCadastro = dtCadastro;
        this.endereco = endereco;
    }


    // ============================
    // 🔥 BUILDER MANUAL (SEM LOMBOK)
    // ============================

    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

    public static class UsuarioBuilder {

        private Long idUsuario;
        private String nome;
        private String email;
        private String senha;
        private String telefone;
        private LocalDateTime dtCadastro = LocalDateTime.now();
        private Endereco endereco;

        public UsuarioBuilder idUsuario(Long idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public UsuarioBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public UsuarioBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UsuarioBuilder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public UsuarioBuilder telefone(String telefone) {
            this.telefone = telefone;
            return this;
        }

        public UsuarioBuilder endereco(Endereco endereco) {
            this.endereco = endereco;
            return this;
        }

        public UsuarioBuilder dtCadastro(LocalDateTime dtCadastro) {
            this.dtCadastro = dtCadastro;
            return this;
        }

        public Usuario build() {
            return new Usuario(
                    idUsuario,
                    nome,
                    email,
                    senha,
                    telefone,
                    dtCadastro,
                    endereco
            );
        }
    }


    // ============================
    // GETTERS E SETTERS
    // ============================

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public LocalDateTime getDtCadastro() { return dtCadastro; }
    public void setDtCadastro(LocalDateTime dtCadastro) { this.dtCadastro = dtCadastro; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }


    // ============================
    // USERDETAILS
    // ============================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    @Override
    public String getPassword() { return this.senha; }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }


    // ============================
    // EQUALS / HASHCODE
    // ============================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(idUsuario, usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }


    // ============================
    // TO STRING
    // ============================

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dtCadastro=" + dtCadastro +
                '}';
    }
}
