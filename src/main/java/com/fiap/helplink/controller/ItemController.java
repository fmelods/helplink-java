package com.fiap.helplink.controller;

import com.fiap.helplink.dto.ItemDTO;
import com.fiap.helplink.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
@Tag(name = "Itens", description = "API para gerenciamento de itens de doação")
@SecurityRequirement(name = "bearerAuth")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os itens (sem paginação)")
    public ResponseEntity<List<ItemDTO>> listar() {
        return ResponseEntity.ok(itemService.listar());
    }

    // NOVO ENDPOINT PAGINADO
    @GetMapping("/page")
    @Operation(summary = "Listar itens com paginação")
    public ResponseEntity<Page<ItemDTO>> listarPaginado(Pageable pageable) {
        return ResponseEntity.ok(itemService.listarPaginado(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID")
    public ResponseEntity<ItemDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.buscar(id));
    }

    @PostMapping("/{usuarioId}")
    @Operation(summary = "Criar novo item")
    public ResponseEntity<ItemDTO> criar(@PathVariable Long usuarioId,
                                         @Valid @RequestBody ItemDTO dto) {

        ItemDTO criado = itemService.criar(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{usuarioId}/{id}")
    @Operation(summary = "Atualizar item")
    public ResponseEntity<ItemDTO> atualizar(@PathVariable Long usuarioId,
                                             @PathVariable Long id,
                                             @Valid @RequestBody ItemDTO dto) {

        ItemDTO atualizado = itemService.atualizar(usuarioId, id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir item")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
