package com.fiap.helplink.controller;

import com.fiap.helplink.dto.PromptDTO;
import com.fiap.helplink.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@Tag(name = "AI", description = "Integração com IA gratuita (HuggingFace)")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/impacto")
    @Operation(summary = "Gerar uma sugestão de impacto usando IA")
    public ResponseEntity<String> gerarMensagemImpacto(@RequestBody PromptDTO dto) {

        if (dto.getPrompt() == null || dto.getPrompt().isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'prompt' não pode estar vazio.");
        }

        String resultado = aiService.gerarImpacto(dto.getPrompt());
        return ResponseEntity.ok(resultado);
    }
}
