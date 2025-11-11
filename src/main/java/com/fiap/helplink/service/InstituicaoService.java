package com.fiap.helplink.service;

import com.fiap.helplink.dto.InstituicaoDTO;
import com.fiap.helplink.model.Endereco;
import com.fiap.helplink.model.Instituicao;
import com.fiap.helplink.repository.EnderecoRepository;
import com.fiap.helplink.repository.InstituicaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstituicaoService {

    @Autowired private InstituicaoRepository instituicaoRepository;
    @Autowired private EnderecoRepository enderecoRepository;

    @Transactional(readOnly = true)
    public List<InstituicaoDTO> listarTodas() {
        return instituicaoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InstituicaoDTO encontrarPorId(Long id) {
        Instituicao instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com ID: " + id));
        return toDTO(instituicao);
    }

    @Transactional
    public InstituicaoDTO criar(InstituicaoDTO dto) {
        if (instituicaoRepository.existsByCnpj(dto.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já registrado: " + dto.getCnpj());
        }

        Instituicao instituicao = Instituicao.builder()
                .nome(dto.getNome())
                .cnpj(dto.getCnpj())
                .email(dto.getEmail())
                .categoriasAceitas(dto.getCategoriasAceitas())
                .telefone(dto.getTelefone())
                .build();

        if (dto.getEndereco() != null && dto.getEndereco().getIdEndereco() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEndereco().getIdEndereco())
                    .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
            instituicao.setEndereco(endereco);
        }

        return toDTO(instituicaoRepository.save(instituicao));
    }

    @Transactional
    public InstituicaoDTO atualizar(Long id, InstituicaoDTO dto) {
        Instituicao instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com ID: " + id));

        instituicao.setNome(dto.getNome());
        instituicao.setEmail(dto.getEmail());
        instituicao.setTelefone(dto.getTelefone());
        instituicao.setCategoriasAceitas(dto.getCategoriasAceitas());

        return toDTO(instituicaoRepository.save(instituicao));
    }

    @Transactional
    public void deletar(Long id) {
        if (!instituicaoRepository.existsById(id)) {
            throw new EntityNotFoundException("Instituição não encontrada com ID: " + id);
        }
        instituicaoRepository.deleteById(id);
    }

    private InstituicaoDTO toDTO(Instituicao instituicao) {
        return InstituicaoDTO.builder()
                .idInstituicao(instituicao.getIdInstituicao())
                .nome(instituicao.getNome())
                .cnpj(instituicao.getCnpj())
                .email(instituicao.getEmail())
                .categoriasAceitas(instituicao.getCategoriasAceitas())
                .telefone(instituicao.getTelefone())
                .build();
    }
}
