package br.com.plusoft.impacthub.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_IMPACTHUB_RESULTADO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResultado;

    private Long idEmpresa;

    private Double pontuacaoFinal;

    private String recomendacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ranking_empresas_id")
    private RankingEmpresas rankingEmpresas;
}

