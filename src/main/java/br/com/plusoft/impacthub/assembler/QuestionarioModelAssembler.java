package br.com.plusoft.impacthub.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.plusoft.impacthub.controller.QuestionarioController;
import br.com.plusoft.impacthub.model.Questionario;

@Component
public class QuestionarioModelAssembler implements RepresentationModelAssembler<Questionario, EntityModel<Questionario>> {

    @Override
    public EntityModel<Questionario> toModel(Questionario questionario) {
        return EntityModel.of(questionario,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QuestionarioController.class).show(questionario.getIdQuestionario())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(QuestionarioController.class).index(null)).withRel("questionario"));
    }
}
