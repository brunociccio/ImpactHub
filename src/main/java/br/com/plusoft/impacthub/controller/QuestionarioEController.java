package br.com.plusoft.impacthub.controller;

import static org.springframework.http.HttpStatus.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.plusoft.impacthub.model.QuestionarioE;
import br.com.plusoft.impacthub.repository.QuestionarioERepository;
import br.com.plusoft.impacthub.assembler.QuestionarioEModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/questionarioE")
@Tag(name = "QuestionarioE", description = "APIs relacionadas ao gerenciamento de questionários ambientais (E)")
@Slf4j
public class QuestionarioEController {

    @Autowired
    private QuestionarioERepository repository;

    @Autowired
    private QuestionarioEModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<QuestionarioE> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Questionários Ambientais (E)", description = "Retorna uma lista de todos os questionários ambientais com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de questionários ambientais retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<QuestionarioE>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os questionários ambientais com paginação");
        Page<QuestionarioE> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Questionário Ambiental (E) por ID", description = "Retorna um questionário ambiental específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questionário ambiental retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário ambiental não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<QuestionarioE> show(@PathVariable Long id) {
        log.info("Buscando questionário ambiental com id {}", id);
        var questionarioE = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário ambiental não encontrado")
        );
        return assembler.toModel(questionarioE);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Questionário Ambiental (E)", description = "Cria um novo questionário ambiental com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Questionário ambiental criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<QuestionarioE>> create(@RequestBody @Valid QuestionarioE questionarioE) {
        log.info("Criando um novo questionário ambiental");
        repository.save(questionarioE);
        var entityModel = assembler.toModel(questionarioE);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Questionário Ambiental (E)", description = "Atualiza os dados de um questionário ambiental existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questionário ambiental atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário ambiental não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<QuestionarioE>> update(@PathVariable Long id, @RequestBody @Valid QuestionarioE questionarioE) {
        log.info("Atualizando questionário ambiental com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário ambiental não encontrado")
        );
        questionarioE.setId(id);
        repository.save(questionarioE);
        return ResponseEntity.ok(assembler.toModel(questionarioE));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Questionário Ambiental (E)", description = "Exclui um questionário ambiental existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Questionário ambiental excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário ambiental não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo questionário ambiental com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário ambiental não encontrado")
        );
        repository.deleteById(id);
    }
}
