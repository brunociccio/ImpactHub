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
@Entity(name = "TB_IMPACTHUB_DOCUMENTOS_AMBIENTAIS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentosAmbientais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nível de Certificado Ambiental é obrigatório")
    private String nivelCertificadoAmbiental;

    @NotNull(message = "Percentual Reciclável é obrigatório")
    private Double percentualReciclavel;

    private String descricaoImpatoAmbiental;
}
