package br.com.plusoft.impacthub.model;

import java.time.LocalDate;

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
@Entity(name = "TB_IMPACTHUB_DOCUMENTOS_CADASTRAIS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentosCadastrais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

    @NotBlank(message = "Inscrição Estadual é obrigatória")
    private String inscricaoEstadual;

    @NotNull(message = "Data de Registro na Junta Comercial é obrigatória")
    private LocalDate dataRegistroJuntaComercial;
}
