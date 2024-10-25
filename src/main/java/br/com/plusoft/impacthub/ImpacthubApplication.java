package br.com.plusoft.impacthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@Controller
public class ImpacthubApplication {

	    public static void main(String[] args) {
        // Carrega as variáveis do arquivo .env
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // Lê a chave da API da OpenAI do arquivo .env
        String openAiApiKey = dotenv.get("OPENAI_API_KEY");
        
        // Inicializa a aplicação Spring Boot
		SpringApplication.run(ImpacthubApplication.class, args);
	}

	@RequestMapping("/home")
    @ResponseBody
    public String home() {
        return "API Full-Stack do Projeto Impact Hub";
    }

}
