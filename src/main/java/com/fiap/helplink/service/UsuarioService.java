package com.fiap.helplink.service;

import com.fiap.helplink.dto.UsuarioCreateDTO;
import com.fiap.helplink.dto.UsuarioDTO;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.model.Endereco;
import com.fiap.helplink.repository.UsuarioRepository;
import com.fiap.helplink.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final PasswordEncoder passwordEncoder;

    // CONSTRUTOR MANUAL
    public UsuarioService(
            UsuarioRepository usuarioRepository,
            EnderecoRepository enderecoRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UsuarioDTO buscar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return toDTO(usuario);
    }

    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return toDTO(usuario);
    }

    public Usuario buscarModel(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public boolean senhaConfere(String digitada, String criptografada) {
        return passwordEncoder.matches(digitada, criptografada);
    }

    @Transactional
    public UsuarioDTO criar(UsuarioCreateDTO dto) {

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setTelefone(dto.getTelefone());

        if (dto.getIdEndereco() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getIdEndereco())
                    .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
            usuario.setEndereco(endereco);
        }

        usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioCreateDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setTelefone(dto.getTelefone());

        if (dto.getIdEndereco() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getIdEndereco())
                    .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
            usuario.setEndereco(endereco);
        }

        usuarioRepository.save(usuario);
        return toDTO(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(u.getIdUsuario());
        dto.setNome(u.getNome());
        dto.setEmail(u.getEmail());
        dto.setTelefone(u.getTelefone());
        dto.setDtCadastro(u.getDtCadastro());
        return dto;
    }
}
