package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.CadastroController;
import br.com.plusoft.impacthub.model.Cadastro;

@Component
public class CadastroModelAssembler implements RepresentationModelAssembler<Cadastro, EntityModel<Cadastro>> {

    @Override
    public EntityModel<Cadastro> toModel(Cadastro cadastro) {
        return EntityModel.of(cadastro,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CadastroController.class).show(cadastro.getIdUsuario())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CadastroController.class).index(null)).withRel("cadastro"));
    }
}
