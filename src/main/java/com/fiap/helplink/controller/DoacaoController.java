package com.fiap.helplink.controller;

import com.fiap.helplink.dto.DoacaoCreateDTO;
import com.fiap.helplink.dto.DoacaoDTO;
import com.fiap.helplink.service.DoacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doacoes")
@Tag(name = "Doações", description = "API para gerenciamento das doações")
@SecurityRequirement(name = "bearerAuth")
public class DoacaoController {

    private final DoacaoService doacaoService;

    public DoacaoController(DoacaoService doacaoService) {
        this.doacaoService = doacaoService;
    }

    // LISTAR TODAS
    @GetMapping
    @Operation(summary = "Listar todas as doações")
    public ResponseEntity<List<DoacaoDTO>> listarTodas() {
        return ResponseEntity.ok(doacaoService.listarTodas());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar doação por ID")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(doacaoService.buscar(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Doação não encontrada");
        }
    }

    // CRIAR NOVA
    @PostMapping
    @Operation(summary = "Criar nova doação")
    public ResponseEntity<?> criar(
            @RequestParam Long usuarioId,
            @Valid @RequestBody DoacaoCreateDTO dto) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(doacaoService.criar(usuarioId, dto));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário ou instituição não encontrados");
        }
    }

    // ALTERAR STATUS
    @PutMapping("/{id}/status")
    @Operation(summary = "Atualizar status da doação")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        try {
            return ResponseEntity.ok(doacaoService.atualizar(id, status));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Doação não encontrada");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Status inválido. Use: ABERTA, CONCLUIDA ou CANCELADA");
        }
    }

    // EXCLUIR
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir doação")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            doacaoService.excluir(id);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Doação não encontrada");
        }
    }
}
