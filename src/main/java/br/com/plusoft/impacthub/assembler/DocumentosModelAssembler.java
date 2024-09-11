package br.com.plusoft.impacthub.assembler;

import br.com.plusoft.impacthub.controller.DocumentosController;
import br.com.plusoft.impacthub.model.Documentos;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class DocumentosModelAssembler implements RepresentationModelAssembler<Documentos, EntityModel<Documentos>> {

    @Override
    public EntityModel<Documentos> toModel(Documentos documento) {
        return EntityModel.of(documento,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosController.class).show(documento.getIdDocumento())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosController.class).index(null)).withRel("documentos"));
    }
}
