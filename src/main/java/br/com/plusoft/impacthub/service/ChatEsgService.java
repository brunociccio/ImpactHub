package br.com.plusoft.impacthub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatEsgService {

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    public String getAnswerFromAi(String question) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + openAiApiKey);
            headers.set("Content-Type", "application/json");

            // Ajuste para respostas mais naturais em perguntas simples
            String prompt;
            if (question.equalsIgnoreCase("Olá") || question.equalsIgnoreCase("Oi")) {
                prompt = "Diga 'Olá! Tudo bem? Sou uma IA especializada em ESG, como posso ajudar?'";
            } else {
                prompt = "Você é um especialista em ESG: " + question;
            }

            String requestBody = "{"
                    + "\"model\": \"gpt-3.5-turbo\","
                    + "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}],"
                    + "\"max_tokens\": 100"
                    + "}";

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.openai.com/v1/chat/completions",
                    HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return extractResponse(response.getBody());
            } else {
                return "Erro: " + response.getStatusCode();
            }

        } catch (Exception e) {
            return "Erro ao chamar a IA: " + e.getMessage();
        }
    }

    // Método para extrair a resposta JSON
    private String extractResponse(String responseBody) {
        try {
            // Parseia o JSON de resposta para extrair o conteúdo
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            
            // Navega até o campo 'choices' e pega o primeiro item, depois extrai 'message.content'
            String message = jsonNode
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
            
            return message;
        } catch (Exception e) {
            return "Erro ao processar a resposta da IA.";
        }
    }
}
