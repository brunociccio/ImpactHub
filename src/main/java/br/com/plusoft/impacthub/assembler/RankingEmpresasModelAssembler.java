package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.RankingEmpresasController;
import br.com.plusoft.impacthub.model.RankingEmpresas;

@Component
public class RankingEmpresasModelAssembler implements RepresentationModelAssembler<RankingEmpresas, EntityModel<RankingEmpresas>> {

    @Override
    public EntityModel<RankingEmpresas> toModel(RankingEmpresas rankingEmpresas) {
        return EntityModel.of(rankingEmpresas,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RankingEmpresasController.class).show(rankingEmpresas.getIdRanking())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RankingEmpresasController.class).index(null)).withRel("rankingEmpresas"));
    }
}
