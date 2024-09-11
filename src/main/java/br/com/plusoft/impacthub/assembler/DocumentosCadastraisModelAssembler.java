package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.DocumentosCadastraisController;
import br.com.plusoft.impacthub.model.DocumentosCadastrais;

@Component
public class DocumentosCadastraisModelAssembler implements RepresentationModelAssembler<DocumentosCadastrais, EntityModel<DocumentosCadastrais>> {

    @Override
    public EntityModel<DocumentosCadastrais> toModel(DocumentosCadastrais documentoCadastral) {
        return EntityModel.of(documentoCadastral,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosCadastraisController.class).show(documentoCadastral.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosCadastraisController.class).index(null)).withRel("documentosCadastrais"));
    }
}
