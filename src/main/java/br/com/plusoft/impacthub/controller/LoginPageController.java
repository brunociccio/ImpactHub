package br.com.plusoft.impacthub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    @GetMapping("/custom-login")
    public String login() {
        return "login"; 
    }
}
