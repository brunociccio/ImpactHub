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

import br.com.plusoft.impacthub.model.QuestionarioG;
import br.com.plusoft.impacthub.repository.QuestionarioGRepository;
import br.com.plusoft.impacthub.assembler.QuestionarioGModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/questionarioG")
@Tag(name = "QuestionarioG", description = "APIs relacionadas ao gerenciamento de questionários de governança (G)")
@Slf4j
public class QuestionarioGController {

    @Autowired
    private QuestionarioGRepository repository;

    @Autowired
    private QuestionarioGModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<QuestionarioG> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Questionários de Governança (G)", description = "Retorna uma lista de todos os questionários de governança com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de questionários de governança retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<QuestionarioG>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os questionários de governança com paginação");
        Page<QuestionarioG> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Questionário de Governança (G) por ID", description = "Retorna um questionário de governança específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questionário de governança retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário de governança não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<QuestionarioG> show(@PathVariable Long id) {
        log.info("Buscando questionário de governança com id {}", id);
        var questionarioG = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário de governança não encontrado")
        );
        return assembler.toModel(questionarioG);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Questionário de Governança (G)", description = "Cria um novo questionário de governança com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Questionário de governança criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<QuestionarioG>> create(@RequestBody @Valid QuestionarioG questionarioG) {
        log.info("Criando um novo questionário de governança");
        repository.save(questionarioG);
        var entityModel = assembler.toModel(questionarioG);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Questionário de Governança (G)", description = "Atualiza os dados de um questionário de governança existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questionário de governança atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário de governança não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<QuestionarioG>> update(@PathVariable Long id, @RequestBody @Valid QuestionarioG questionarioG) {
        log.info("Atualizando questionário de governança com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário de governança não encontrado")
        );
        questionarioG.setId(id);
        repository.save(questionarioG);
        return ResponseEntity.ok(assembler.toModel(questionarioG));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Questionário de Governança (G)", description = "Exclui um questionário de governança existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Questionário de governança excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário de governança não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo questionário de governança com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário de governança não encontrado")
        );
        repository.deleteById(id);
    }
}
