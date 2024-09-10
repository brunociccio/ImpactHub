package br.com.plusoft.impacthub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_IMPACTHUB_CADASTRO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Data de Nascimento é obrigatória")
    private String dataNascimento;

    @NotBlank(message = "Cargo é obrigatório")
    private String cargo;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @ManyToOne
    @JoinColumn(name = "cadastro_cnpj_id")
    private CadastroCnpj cadastroCnpj;

    @ManyToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "documentos_id")
    private Documentos documentos;

    @ManyToOne
    @JoinColumn(name = "login_id")
    private Login login;
}
