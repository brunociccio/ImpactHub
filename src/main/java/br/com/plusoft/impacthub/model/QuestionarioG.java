package br.com.plusoft.impacthub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador da entidade

    private Double percentualConselhoIndependente;
    private String frequenciaAuditoriasInternas;
    private int quantidadePoliticasAnticorrupcao;
    private Boolean divulgacaoTransparenteRelatorios;
    private String descricaoGovernancaDigital;
}
