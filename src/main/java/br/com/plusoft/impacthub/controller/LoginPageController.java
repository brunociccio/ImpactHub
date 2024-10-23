package br.com.plusoft.impacthub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginPageController {

    // Usuário e senha fixos para fins de teste
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin123";

    @PostMapping("/perform_login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Verifica se o usuário e senha correspondem aos valores fixos
        if (DEFAULT_USERNAME.equals(username) && DEFAULT_PASSWORD.equals(password)) {
            return "redirect:/dashboard"; // Redireciona para o dashboard se login for bem-sucedido
        } else {
            return "redirect:/custom-login?error=true"; // Redireciona para a página de login com mensagem de erro
        }
    }


    @GetMapping("/custom-login")
    public String loginPage() {
        return "login"; // Retorna o template Thymeleaf da página de login
    }
}
