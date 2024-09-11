package br.com.plusoft.impacthub.assembler;

import br.com.plusoft.impacthub.controller.RecuperacaoSenhaController;
import br.com.plusoft.impacthub.model.RecuperacaoSenha;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class RecuperacaoSenhaModelAssembler implements RepresentationModelAssembler<RecuperacaoSenha, EntityModel<RecuperacaoSenha>> {

    @Override
    public EntityModel<RecuperacaoSenha> toModel(RecuperacaoSenha recuperacaoSenha) {
        return EntityModel.of(recuperacaoSenha,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RecuperacaoSenhaController.class).show(recuperacaoSenha.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RecuperacaoSenhaController.class).index(null)).withRel("recuperacaoSenha"));
    }
}
