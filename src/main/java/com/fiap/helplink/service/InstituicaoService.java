package com.fiap.helplink.service;

import com.fiap.helplink.dto.InstituicaoDTO;
import com.fiap.helplink.model.Instituicao;
import com.fiap.helplink.repository.InstituicaoRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository) {
        this.instituicaoRepository = instituicaoRepository;
    }

    /* ============================================================
       LISTAR TODAS (COM CACHE)
     ============================================================ */
    @Transactional(readOnly = true)
    @Cacheable("instituicoes")
    public List<InstituicaoDTO> listar() {
        return instituicaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /* ============================================================
       BUSCAR POR ID
     ============================================================ */
    @Transactional(readOnly = true)
    public InstituicaoDTO buscar(Long id) {
        Instituicao inst = instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada"));
        return toDTO(inst);
    }

    /* ============================================================
       CRIAR NOVA INSTITUIÇÃO
     ============================================================ */
    @Transactional
    @CacheEvict(value = "instituicoes", allEntries = true)
    public InstituicaoDTO criar(InstituicaoDTO dto) {

        if (instituicaoRepository.existsByCnpj(dto.getCnpj())) {
            throw new DataIntegrityViolationException("Já existe uma instituição com este CNPJ.");
        }

        Instituicao inst = new Instituicao();
        copyDtoToEntity(dto, inst);

        return toDTO(instituicaoRepository.save(inst));
    }

    /* ============================================================
       ATUALIZAR INSTITUIÇÃO
     ============================================================ */
    @Transactional
    @CacheEvict(value = "instituicoes", allEntries = true)
    public InstituicaoDTO atualizar(Long id, InstituicaoDTO dto) {

        Instituicao inst = instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada"));

        boolean cnpjUsadoPorOutro =
                instituicaoRepository.existsByCnpjAndIdInstituicaoNot(dto.getCnpj(), id);

        if (cnpjUsadoPorOutro) {
            throw new DataIntegrityViolationException("CNPJ já está sendo usado por outra instituição.");
        }

        copyDtoToEntity(dto, inst);

        return toDTO(instituicaoRepository.save(inst));
    }

    /* ============================================================
       EXCLUIR INSTITUIÇÃO
     ============================================================ */
    @Transactional
    @CacheEvict(value = "instituicoes", allEntries = true)
    public void deletar(Long id) {

        Instituicao inst = instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada"));

        try {
            instituicaoRepository.delete(inst);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                    "Não é possível excluir esta instituição pois existem doações vinculadas.");
        }
    }

    /* ============================================================
       CONTAGEM
     ============================================================ */
    @Transactional(readOnly = true)
    public long countAll() {
        return instituicaoRepository.count();
    }

    /* ============================================================
       MÉTODO DE CONVERSÃO DTO -> ENTIDADE
     ============================================================ */
    private void copyDtoToEntity(InstituicaoDTO dto, Instituicao inst) {
        inst.setNome(dto.getNome() != null ? dto.getNome().trim() : null);
        inst.setCnpj(dto.getCnpj() != null ? dto.getCnpj().trim() : null);
        inst.setEmail(dto.getEmail() != null ? dto.getEmail().trim() : null);
        inst.setTelefone(dto.getTelefone());
        inst.setCategoriasAceitas(dto.getCategoriasAceitas());
    }

    /* ============================================================
       MÉTODO DE CONVERSÃO ENTIDADE -> DTO
     ============================================================ */
    private InstituicaoDTO toDTO(Instituicao i) {
        InstituicaoDTO dto = new InstituicaoDTO();
        dto.setIdInstituicao(i.getIdInstituicao());
        dto.setNome(i.getNome());
        dto.setCnpj(i.getCnpj());
        dto.setEmail(i.getEmail());
        dto.setTelefone(i.getTelefone());
        dto.setCategoriasAceitas(i.getCategoriasAceitas());
        return dto;
    }
}
