package br.com.plusoft.impacthub.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
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
@Entity(name = "TB_IMPACTHUB_CADASTRO_CNPJ")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastroCnpj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpresa;

    @NotBlank(message = "Política Ambiental é obrigatória")
    private String politicaAmbiental;

    @NotNull(message = "Emissão de CO2 é obrigatória")
    private double emissaoCO2;

    @NotNull(message = "Uso de recursos renováveis é obrigatório")
    private Boolean usoRecursosRenovaveis;

    @ElementCollection
    private List<String> certificacoesAmbientais;

    @NotBlank(message = "Política de Diversidade e Inclusão é obrigatória")
    private String politicaDiversidadeInclusao;

    @NotNull(message = "Número de funcionários é obrigatório")
    private int numeroFuncionarios;

    @NotNull(message = "Percentual de Diversidade é obrigatório")
    private double percentualDiversidade;

    @NotBlank(message = "Política de Governança é obrigatória")
    private String politicaGovernanca;

    @NotNull(message = "Conselho Independente é obrigatório")
    private Boolean conselhoIndependente;

    @NotNull(message = "Transparência em Relatórios é obrigatória")
    private Boolean transparenciaRelatorios;

    @NotNull(message = "Pontuação ESG é obrigatória")
    private double pontuacaoESG;

    @NotNull(message = "Data de Cadastro é obrigatória")
    private LocalDate dataCadastro;

    @NotNull(message = "Status Ativo é obrigatório")
    private Boolean statusAtivo;
}
