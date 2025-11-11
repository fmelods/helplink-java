package com.fiap.helplink.controller;

import com.fiap.helplink.dto.InstituicaoDTO;
import com.fiap.helplink.service.InstituicaoService;
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
@RequestMapping("/api/instituicoes")
@Tag(name = "Instituições", description = "API para gerenciamento de ONGs e abrigos")
@SecurityRequirement(name = "bearerAuth")
public class InstituicaoController {

    @Autowired
    private InstituicaoService instituicaoService;

    @GetMapping
    @Operation(summary = "Listar todas as instituições")
    public ResponseEntity<List<InstituicaoDTO>> listarTodas() {
        return ResponseEntity.ok(instituicaoService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instituição por ID")
    public ResponseEntity<InstituicaoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(instituicaoService.encontrarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar nova instituição")
    public ResponseEntity<InstituicaoDTO> criar(@Valid @RequestBody InstituicaoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instituicaoService.criar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar instituição")
    public ResponseEntity<InstituicaoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody InstituicaoDTO dto) {
        return ResponseEntity.ok(instituicaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir instituição")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        instituicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
