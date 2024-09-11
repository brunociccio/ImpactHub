package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.CadastroCnpjController;
import br.com.plusoft.impacthub.model.CadastroCnpj;

@Component
public class CadastroCnpjModelAssembler implements RepresentationModelAssembler<CadastroCnpj, EntityModel<CadastroCnpj>> {

    @Override
    public EntityModel<CadastroCnpj> toModel(CadastroCnpj cadastroCnpj) {
        return EntityModel.of(cadastroCnpj,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CadastroCnpjController.class).show(cadastroCnpj.getIdEmpresa())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CadastroCnpjController.class).index(null)).withRel("cadastroCnpj"));
    }
}
