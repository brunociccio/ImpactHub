package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.ResultadoController;
import br.com.plusoft.impacthub.model.Resultado;

@Component
public class ResultadoModelAssembler implements RepresentationModelAssembler<Resultado, EntityModel<Resultado>> {

    @Override
    public EntityModel<Resultado> toModel(Resultado resultado) {
        return EntityModel.of(resultado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ResultadoController.class).show(resultado.getIdResultado())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ResultadoController.class).index(null)).withRel("resultado"));
    }
}
