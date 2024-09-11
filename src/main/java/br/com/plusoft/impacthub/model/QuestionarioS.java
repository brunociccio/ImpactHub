package br.com.plusoft.impacthub.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_IMPACTHUB_QUESTIONARIO_S")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionarioS {

    private Double indiceSatisfacaoFuncionarios;

    private Double taxaRotatividade;

    private Double percentualDiversidade;

    private Double investimentoComunitarioAnual;

    private String programaBemEstar;
}
