package com.fiap.helplink.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public String gerarImpacto(String prompt) {
        try {
            String url = "http://localhost:11434/api/generate";

            // Payload do Ollama
            String payload = """
            {
                "model": "llama3",
                "prompt": "%s",
                "stream": false
            }
            """.formatted(prompt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response =
                    rest.exchange(url, HttpMethod.POST, entity, String.class);

            JsonNode json = mapper.readTree(response.getBody());

            return json.get("response").asText();

        } catch (Exception e) {
            return "Erro ao chamar IA local (Ollama): " + e.getMessage();
        }
    }
}
