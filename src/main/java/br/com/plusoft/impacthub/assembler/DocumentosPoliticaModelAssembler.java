package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.DocumentosPoliticaController;
import br.com.plusoft.impacthub.model.DocumentosPolitica;

@Component
public class DocumentosPoliticaModelAssembler implements RepresentationModelAssembler<DocumentosPolitica, EntityModel<DocumentosPolitica>> {

    @Override
    public EntityModel<DocumentosPolitica> toModel(DocumentosPolitica documentoPolitica) {
        return EntityModel.of(documentoPolitica,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosPoliticaController.class).show(documentoPolitica.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosPoliticaController.class).index(null)).withRel("documentosPolitica"));
    }
}
