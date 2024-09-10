package br.com.plusoft.impacthub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "TB_IMPACTHUB_LOGIN")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome de usuário é obrigatório")
    private String nomeUsuario;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotNull(message = "Status de Ativo é obrigatório")
    private Boolean ativo;

    @NotNull(message = "Número de tentativas de login é obrigatório")
    private int tentativasLogin;

    private LocalDateTime ultimaTentativaLogin;

    private LocalDateTime ultimoLogin;

    @ManyToOne
    @JoinColumn(name = "recuperacao_senha_id")
    private RecuperacaoSenha recuperacaoSenha;
}
