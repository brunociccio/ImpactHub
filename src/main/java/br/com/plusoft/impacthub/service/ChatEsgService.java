package br.com.plusoft.impacthub.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class ChatEsgService {

    // Carrega a variável de ambiente da API key
    private final Dotenv dotenv = Dotenv.configure().load();
    private final String openAiApiKey = dotenv.get("OPENAI_API_KEY");

    public String getAnswerFromAi(String question) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + openAiApiKey);
            headers.set("Content-Type", "application/json");
    
            // Ajuste para saudações e restrição ao tema ESG
            String prompt;
            if (isGreeting(question)) {
                prompt = "Diga 'Olá! Tudo bem? Sou uma IA especializada em ESG, como posso ajudar?'";
            } else if (!isRelatedToESG(question)) { 
                // Retorna uma resposta padrão se o assunto for irrelevante
                return "Posso ajudar somente com assuntos sobre ESG. Por favor, pergunte algo sobre meio ambiente, sustentabilidade, governança ou responsabilidade social.";
            } else {
                prompt = "Você é um especialista em ESG. Responda apenas a perguntas sobre ESG. Pergunta: " + question;
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
    
    // Método para verificar saudações
    private boolean isGreeting(String question) {
        String[] greetings = {"oi", "olá", "ola", "tudo bem", "bom dia", "boa tarde", "boa noite", "cordialidades"};
        for (String greeting : greetings) {
            if (question.toLowerCase().contains(greeting)) {
                return true;
            }
        }
        return false;
    }
    
    // Método para verificar se a pergunta é relacionada ao ESG
    private boolean isRelatedToESG(String question) {
        String[] esgKeywords = {"meio ambiente", "sustentabilidade", "governança", "responsabilidade social", "esg"};
        for (String keyword : esgKeywords) {
            if (question.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
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
