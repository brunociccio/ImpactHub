package br.com.plusoft.impacthub.assembler;

import br.com.plusoft.impacthub.controller.QuestionarioGController;
import br.com.plusoft.impacthub.model.QuestionarioG;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class QuestionarioGModelAssembler implements RepresentationModelAssembler<QuestionarioG, EntityModel<QuestionarioG>> {

    @Override
    public EntityModel<QuestionarioG> toModel(QuestionarioG questionarioG) {
        return EntityModel.of(questionarioG,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QuestionarioGController.class).show(questionarioG.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QuestionarioGController.class).index(null)).withRel("questionarioG"));
    }
}
