package com.fiap.helplink.service;

import com.fiap.helplink.dto.CategoriaDTO;
import com.fiap.helplink.model.Categoria;
import com.fiap.helplink.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // ===============================
    // LISTAR (SITE + API) - COM CACHE
    // ===============================
    @Transactional(readOnly = true)
    @Cacheable("categorias")
    public List<CategoriaDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Alias para compatibilidade com o Swagger / API
    @Transactional(readOnly = true)
    public List<CategoriaDTO> listarTodas() {
        return listar();
    }

    // ===============================
    // BUSCAR POR ID
    // ===============================
    @Transactional(readOnly = true)
    public CategoriaDTO buscar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrado"));
        return toDTO(categoria);
    }

    // Alias usado no Controller da API
    @Transactional(readOnly = true)
    public CategoriaDTO encontrarPorId(Long id) {
        return buscar(id);
    }

    // ===============================
    // CRIAR  (limpa cache)
    // ===============================
    @Transactional
    @CacheEvict(value = "categorias", allEntries = true)
    public CategoriaDTO criar(CategoriaDTO dto) {

        if (categoriaRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Categoria já existe");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());

        return toDTO(categoriaRepository.save(categoria));
    }

    // ===============================
    // ATUALIZAR (limpa cache)
    // ===============================
    @Transactional
    @CacheEvict(value = "categorias", allEntries = true)
    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        categoria.setNome(dto.getNome());

        return toDTO(categoriaRepository.save(categoria));
    }

    // ===============================
    // DELETAR (limpa cache)
    // ===============================
    @Transactional
    @CacheEvict(value = "categorias", allEntries = true)
    public void deletar(Long id) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        categoriaRepository.delete(categoria);
    }

    // ===============================
    // CONVERSOR
    // ===============================
    private CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNome(categoria.getNome());
        return dto;
    }
}
