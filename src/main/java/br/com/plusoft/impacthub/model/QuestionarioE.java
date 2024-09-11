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
@Entity(name = "TB_IMPACTHUB_QUESTIONARIO_E")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionarioE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Adicionando o identificador da entidade

    private String nivelEmissaoCO2;
    private Double percentualUsoEnergiaRenovavel;
    private int numeroCertificacoesAmbientais;
    private Double quantidadeResiduosGerados;
    private String descricaoProjetosSustentabilidade;
}
