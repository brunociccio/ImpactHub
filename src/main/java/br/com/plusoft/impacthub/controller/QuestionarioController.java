package br.com.plusoft.impacthub.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.plusoft.impacthub.assembler.QuestionarioModelAssembler;
import br.com.plusoft.impacthub.model.Questionario;
import br.com.plusoft.impacthub.repository.QuestionarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/questionario")
@Tag(name = "Questionario", description = "APIs relacionadas ao gerenciamento de questionários")
@Slf4j
public class QuestionarioController {

    @Autowired
    private QuestionarioRepository repository;

    @Autowired
    private QuestionarioModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Questionario> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Questionários", description = "Retorna uma lista de todos os questionários com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de questionários retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Questionario>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os questionários com paginação");
        Page<Questionario> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Questionário por ID", description = "Retorna um questionário específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questionário retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Questionario> show(@PathVariable Long id) {
        log.info("Buscando questionário com id {}", id);
        var questionario = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário não encontrado")
        );
        return assembler.toModel(questionario);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Questionário", description = "Cria um novo questionário com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Questionário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Questionario>> create(@RequestBody @Valid Questionario questionario) {
        log.info("Criando um novo questionário");
        repository.save(questionario);
        var entityModel = assembler.toModel(questionario);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Questionário", description = "Atualiza os dados de um questionário existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questionário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Questionario>> update(@PathVariable Long id, @RequestBody @Valid Questionario questionario) {
        log.info("Atualizando questionário com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário não encontrado")
        );
        questionario.setIdQuestionario(id);
        repository.save(questionario);
        return ResponseEntity.ok(assembler.toModel(questionario));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Questionário", description = "Exclui um questionário existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Questionário excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo questionário com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário não encontrado")
        );
        repository.deleteById(id);
    }
}
