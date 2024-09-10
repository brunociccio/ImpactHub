package br.com.plusoft.impacthub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class ImpacthubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImpacthubApplication.class, args);
	}

	@RequestMapping("/home")
    @ResponseBody
    public String home() {
        return "API Back-End do Projeto ImpactHub: Challenge da Plusoft ";
    }

}
