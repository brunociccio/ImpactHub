package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.QuestionarioEController;
import br.com.plusoft.impacthub.model.QuestionarioE;

@Component
public class QuestionarioEModelAssembler implements RepresentationModelAssembler<QuestionarioE, EntityModel<QuestionarioE>> {

    @Override
    public EntityModel<QuestionarioE> toModel(QuestionarioE questionarioE) {
        return EntityModel.of(questionarioE,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QuestionarioEController.class).show(questionarioE.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QuestionarioEController.class).index(null)).withRel("questionarioE"));
    }
}

