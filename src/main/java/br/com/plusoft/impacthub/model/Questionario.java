package br.com.plusoft.impacthub.model;

import java.time.LocalDate;

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
@Entity(name = "TB_IMPACTHUB_QUESTIONARIO")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Questionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuestionario;

    private Long idEmpresa;

    private LocalDate dataResposta;

    private String descricao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionario_e_id")
    private QuestionarioE questionarioE;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionario_s_id")
    private QuestionarioS questionarioS;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "questionario_g_id")
    private QuestionarioG questionarioG;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "relatorio_id")
    private Relatorio relatorio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resultado_id")
    private Resultado resultado;
}
