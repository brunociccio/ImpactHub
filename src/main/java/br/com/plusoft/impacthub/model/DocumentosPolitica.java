package br.com.plusoft.impacthub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_IMPACTHUB_DOCUMENTOS_POLITICA")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentosPolitica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Inclusão e Diversidade é obrigatória")
    private Boolean inclusaoDiversidade;

    @NotNull(message = "Política Anticorrupção é obrigatória")
    private Boolean politicaAnticorrupcao;

    @NotBlank(message = "Descrição da Política de Governança é obrigatória")
    private String descricaoPoliticaGovernanca;
}
