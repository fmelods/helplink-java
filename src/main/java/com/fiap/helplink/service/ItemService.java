package com.fiap.helplink.service;

import com.fiap.helplink.dto.ItemDTO;
import com.fiap.helplink.model.Categoria;
import com.fiap.helplink.model.Item;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.repository.CategoriaRepository;
import com.fiap.helplink.repository.ItemRepository;
import com.fiap.helplink.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public ItemService(
            ItemRepository itemRepository,
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.itemRepository = itemRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // ============================
    // LISTAR TODOS
    // ============================
    @Transactional(readOnly = true)
    public List<ItemDTO> listar() {
        return itemRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ============================
    // LISTAR POR USUÁRIO
    // ============================
    @Transactional(readOnly = true)
    public List<ItemDTO> listarPorUsuario(Long usuarioId) {
        return itemRepository.findByUsuario_IdUsuario(usuarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ============================
    // CONTADOR PARA DASHBOARD
    // ============================
    @Transactional(readOnly = true)
    public long countByUsuario(Long usuarioId) {
        return itemRepository.countByUsuario_IdUsuario(usuarioId);
    }

    // ============================
    // BUSCAR POR ID
    // ============================
    @Transactional(readOnly = true)
    public ItemDTO buscar(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));
        return toDTO(item);
    }

    // ============================
    // CRIAR
    // ============================
    @Transactional
    public ItemDTO criar(Long usuarioId, ItemDTO dto) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Item item = new Item();
        item.setTitulo(dto.getTitulo());
        item.setEstadoConservacao(dto.getEstadoConservacao());
        item.setFotoUrl(dto.getFotoUrl());
        item.setDescricao(dto.getDescricao());
        item.setUsuario(usuario);
        item.setCategoria(categoria);

        return toDTO(itemRepository.save(item));
    }

    // ============================
    // ATUALIZAR  (NOVO FORMATO)
    // ============================
    @Transactional
    public ItemDTO atualizar(Long id, Long usuarioId, ItemDTO dto) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

        // Garante que o item pertence ao usuário (opcional porém correto)
        if (!item.getUsuario().getIdUsuario().equals(usuarioId)) {
            throw new RuntimeException("Este item não pertence ao usuário informado");
        }

        // Atualiza categoria, se alterada
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
            item.setCategoria(categoria);
        }

        item.setTitulo(dto.getTitulo());
        item.setEstadoConservacao(dto.getEstadoConservacao());
        item.setFotoUrl(dto.getFotoUrl());
        item.setDescricao(dto.getDescricao());

        return toDTO(itemRepository.save(item));
    }

    // ============================
    // EXCLUIR
    // ============================
    @Transactional
    public void excluir(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item não encontrado");
        }
        itemRepository.deleteById(id);
    }

    // ============================
    // CONVERTER PARA DTO
    // ============================
    private ItemDTO toDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setIdItem(item.getIdItem());
        dto.setTitulo(item.getTitulo());
        dto.setFotoUrl(item.getFotoUrl());
        dto.setEstadoConservacao(item.getEstadoConservacao());
        dto.setDescricao(item.getDescricao());
        dto.setDtRegistro(item.getDtRegistro());
        dto.setUsuarioId(item.getUsuario().getIdUsuario());
        dto.setUsuarioNome(item.getUsuario().getNome());
        dto.setCategoriaId(item.getCategoria().getIdCategoria());
        dto.setCategoriaNome(item.getCategoria().getNome());
        return dto;
    }
}
