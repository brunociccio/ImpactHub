package br.com.plusoft.impacthub.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_IMPACTHUB_RECUPERACAO_SENHA")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecuperacaoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Token de recuperação é obrigatório")
    private String tokenRecuperacao;

    @NotNull(message = "Data de geração do token é obrigatória")
    private LocalDateTime dataGeracaoToken;

    @NotNull(message = "Data de expiração do token é obrigatória")
    private LocalDateTime dataExpiracaoToken;

    @NotBlank(message = "Pergunta de segurança é obrigatória")
    private String perguntaSeguranca;

    @NotBlank(message = "Resposta de segurança é obrigatória")
    private String respostaSeguranca;
}
