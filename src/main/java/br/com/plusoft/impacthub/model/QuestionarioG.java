package br.com.plusoft.impacthub.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_IMPACTHUB_QUESTIONARIO_G")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionarioG {

    private Double percentualConselhoIndependente;

    private String frequenciaAuditoriasInternas;

    private int quantidadePoliticasAnticorrupcao;

    private Boolean divulgacaoTransparenteRelatorios;

    private String descricaoGovernancaDigital;
}
