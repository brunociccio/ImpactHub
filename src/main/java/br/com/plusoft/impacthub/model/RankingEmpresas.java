package br.com.plusoft.impacthub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "TB_IMPACTHUB_RANKING_EMPRESAS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingEmpresas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRanking;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id")
    private List<Empresas> melhoresEmpresas;

    private LocalDate dataAtualizacao;
}

