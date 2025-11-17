package com.fiap.helplink.controller;

import com.fiap.helplink.dto.ItemDTO;
import com.fiap.helplink.service.ItemService;
import com.fiap.helplink.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
@RequiredArgsConstructor
@Tag(name = "Itens", description = "API para gerenciamento de itens de doação")
@SecurityRequirement(name = "bearerAuth")
public class ItemController {

    private final ItemService itemService;
    private final UsuarioService usuarioService;

    // ==========================================
    // GET — LISTAR TODOS
    // ==========================================
    @GetMapping
    @Operation(summary = "Listar todos os itens")
    public ResponseEntity<List<ItemDTO>> listar() {
        return ResponseEntity.ok(itemService.listar());
    }

    // ==========================================
    // GET — BUSCAR POR ID
    // ==========================================
    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID")
    public ResponseEntity<ItemDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.buscar(id));
    }

    // ==========================================
    // POST — CRIAR ITEM PARA O USUÁRIO LOGADO
    // ==========================================
    @PostMapping
    @Operation(summary = "Criar um novo item para o usuário logado")
    public ResponseEntity<ItemDTO> criar(
            @AuthenticationPrincipal String email,
            @Valid @RequestBody ItemDTO dto
    ) {
        Long usuarioId = usuarioService.buscarModel(email).getIdUsuario();
        ItemDTO criado = itemService.criar(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // ==========================================
    // PUT — ATUALIZAR ITEM
    // ==========================================
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar item existente")
    public ResponseEntity<ItemDTO> atualizar(
            @PathVariable Long id,
            @AuthenticationPrincipal String email,
            @Valid @RequestBody ItemDTO dto
    ) {
        Long usuarioId = usuarioService.buscarModel(email).getIdUsuario();
        ItemDTO atualizado = itemService.atualizar(id, usuarioId, dto);
        return ResponseEntity.ok(atualizado);
    }

    // ==========================================
    // DELETE — REMOVER ITEM
    // ==========================================
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir item")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
