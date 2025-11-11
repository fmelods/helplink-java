package com.fiap.helplink.controller;

import com.fiap.helplink.dto.DoacaoCreateDTO;
import com.fiap.helplink.dto.DoacaoDTO;
import com.fiap.helplink.service.DoacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doacoes")
@Tag(name = "Doações", description = "Endpoints de gerenciamento de doações")
@SecurityRequirement(name = "Bearer JWT")
public class DoacaoController {

    @Autowired
    private DoacaoService doacaoService;

    @GetMapping
    @Operation(summary = "Listar todas as doações", description = "Retorna uma lista com todas as doações registradas")
    public ResponseEntity<List<DoacaoDTO>> listarTodas() {
        List<DoacaoDTO> doacoes = doacaoService.listarTodas();
        return ResponseEntity.ok(doacoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar doação por ID", description = "Retorna os detalhes de uma doação específica")
    public ResponseEntity<DoacaoDTO> encontrarPorId(@PathVariable Long id) {
        DoacaoDTO doacao = doacaoService.encontrarPorId(id);
        return ResponseEntity.ok(doacao);
    }

    @PostMapping
    @Operation(summary = "Criar nova doação", description = "Cria uma nova doação associando itens a uma instituição")
    public ResponseEntity<DoacaoDTO> criar(
            @RequestParam Long usuarioId,
            @Valid @RequestBody DoacaoCreateDTO dto) {
        DoacaoDTO doacao = doacaoService.criar(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(doacao);
    }

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Atualizar status da doação",
            description = "Atualiza o status da doação (valores válidos: ABERTA, CONCLUIDA, CANCELADA)"
    )
    public ResponseEntity<DoacaoDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        DoacaoDTO atualizada = doacaoService.atualizar(id, status);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar doação", description = "Remove uma doação do sistema")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        doacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
