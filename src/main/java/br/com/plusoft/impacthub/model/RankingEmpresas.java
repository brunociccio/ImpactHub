package br.com.plusoft.impacthub.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_IMPACTHUB_RANKING_EMPRESAS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingEmpresas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRanking;

    @OneToMany
    @JoinColumn(name = "empresa_id")  // Refere-se ao idUsuario de Cadastro que representa a empresa
    private List<Cadastro> melhoresEmpresas;

    private LocalDate dataAtualizacao;
}


