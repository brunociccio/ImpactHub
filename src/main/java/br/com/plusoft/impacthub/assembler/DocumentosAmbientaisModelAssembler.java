package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.DocumentosAmbientaisController;
import br.com.plusoft.impacthub.model.DocumentosAmbientais;

@Component
public class DocumentosAmbientaisModelAssembler implements RepresentationModelAssembler<DocumentosAmbientais, EntityModel<DocumentosAmbientais>> {

    @Override
    public EntityModel<DocumentosAmbientais> toModel(DocumentosAmbientais documentoAmbiental) {
        return EntityModel.of(documentoAmbiental,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosAmbientaisController.class).show(documentoAmbiental.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosAmbientaisController.class).index(null)).withRel("documentosAmbientais"));
    }
}
