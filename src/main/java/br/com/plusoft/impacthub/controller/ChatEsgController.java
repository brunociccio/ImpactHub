package br.com.plusoft.impacthub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.plusoft.impacthub.service.ChatEsgService;

@Controller  // Use @Controller para servir o template HTML
public class ChatEsgController {

    @Autowired
    private ChatEsgService chatEsgService;

    // Método que serve a página HTML corretamente
    @GetMapping("/chatEsg")
    public String showChatEsgPage() {
        return "chatEsg";  // Certifique-se de que o arquivo chatEsg.html está na pasta templates
    }
}

@RestController  // Use @RestController para a comunicação via AJAX
@RequestMapping("/chatEsg")
class ChatEsgRestController {

    @Autowired
    private ChatEsgService chatEsgService;

    // Endpoint para receber as perguntas e responder via IA
    @PostMapping("/ask")
    public String askQuestion(@RequestParam String question) {
        System.out.println("Recebendo a pergunta: " + question);  // Log para depuração
        String response = chatEsgService.getAnswerFromAi(question);
        System.out.println("Resposta da IA: " + response);  // Log para depuração
        return response;  // Retorna a resposta da IA
    }
}

