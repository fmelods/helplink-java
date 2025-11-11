package com.fiap.helplink.service;

import com.fiap.helplink.dto.ItemDTO;
import com.fiap.helplink.model.Categoria;
import com.fiap.helplink.model.Item;
import com.fiap.helplink.model.Usuario;
import com.fiap.helplink.repository.CategoriaRepository;
import com.fiap.helplink.repository.ItemRepository;
import com.fiap.helplink.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired private ItemRepository itemRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<ItemDTO> listarTodos() {
        return itemRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemDTO encontrarPorId(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com ID: " + id));
        return toDTO(item);
    }

    @Transactional(readOnly = true)
    public List<ItemDTO> listarPorUsuario(Long usuarioId) {
        return itemRepository.findByUsuarioIdUsuario(usuarioId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemDTO criar(ItemDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Item item = Item.builder()
                .titulo(dto.getTitulo())
                .fotoUrl(dto.getFotoUrl())
                .estadoConservacao(dto.getEstadoConservacao())
                .descricao(dto.getDescricao())
                .usuario(usuario)
                .categoria(categoria)
                .build();

        return toDTO(itemRepository.save(item));
    }

    @Transactional
    public ItemDTO atualizar(Long id, ItemDTO dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado com ID: " + id));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        item.setTitulo(dto.getTitulo());
        item.setDescricao(dto.getDescricao());
        item.setFotoUrl(dto.getFotoUrl());
        item.setEstadoConservacao(dto.getEstadoConservacao());
        item.setCategoria(categoria);
        item.setUsuario(usuario);

        return toDTO(itemRepository.save(item));
    }

    @Transactional
    public void deletar(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item não encontrado com ID: " + id);
        }
        itemRepository.deleteById(id);
    }

    private ItemDTO toDTO(Item item) {
        return ItemDTO.builder()
                .idItem(item.getIdItem())
                .titulo(item.getTitulo())
                .fotoUrl(item.getFotoUrl())
                .estadoConservacao(item.getEstadoConservacao())
                .descricao(item.getDescricao())
                .dtRegistro(item.getDtRegistro())
                .usuarioId(item.getUsuario().getIdUsuario())
                .usuarioNome(item.getUsuario().getNome())
                .categoriaId(item.getCategoria().getIdCategoria())
                .categoriaNome(item.getCategoria().getNome())
                .build();
    }
}
