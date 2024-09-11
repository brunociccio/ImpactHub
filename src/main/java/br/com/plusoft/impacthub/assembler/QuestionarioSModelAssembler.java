package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.QuestionarioSController;
import br.com.plusoft.impacthub.model.QuestionarioS;

@Component
public class QuestionarioSModelAssembler implements RepresentationModelAssembler<QuestionarioS, EntityModel<QuestionarioS>> {

    @Override
    public EntityModel<QuestionarioS> toModel(QuestionarioS questionarioS) {
        return EntityModel.of(questionarioS,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QuestionarioSController.class).show(questionarioS.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QuestionarioSController.class).index(null)).withRel("questionarioS"));
    }
}
