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

import br.com.plusoft.impacthub.model.QuestionarioS;
import br.com.plusoft.impacthub.repository.QuestionarioSRepository;
import br.com.plusoft.impacthub.assembler.QuestionarioSModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/questionarioS")
@Tag(name = "QuestionarioS", description = "APIs relacionadas ao gerenciamento de questionários sociais (S)")
@Slf4j
public class QuestionarioSController {

    @Autowired
    private QuestionarioSRepository repository;

    @Autowired
    private QuestionarioSModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<QuestionarioS> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Questionários Sociais (S)", description = "Retorna uma lista de todos os questionários sociais com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de questionários sociais retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<QuestionarioS>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os questionários sociais com paginação");
        Page<QuestionarioS> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Questionário Social (S) por ID", description = "Retorna um questionário social específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questionário social retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário social não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<QuestionarioS> show(@PathVariable Long id) {
        log.info("Buscando questionário social com id {}", id);
        var questionarioS = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário social não encontrado")
        );
        return assembler.toModel(questionarioS);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Questionário Social (S)", description = "Cria um novo questionário social com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Questionário social criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<QuestionarioS>> create(@RequestBody @Valid QuestionarioS questionarioS) {
        log.info("Criando um novo questionário social");
        repository.save(questionarioS);
        var entityModel = assembler.toModel(questionarioS);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Questionário Social (S)", description = "Atualiza os dados de um questionário social existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questionário social atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário social não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<QuestionarioS>> update(@PathVariable Long id, @RequestBody @Valid QuestionarioS questionarioS) {
        log.info("Atualizando questionário social com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário social não encontrado")
        );
        questionarioS.setId(id);
        repository.save(questionarioS);
        return ResponseEntity.ok(assembler.toModel(questionarioS));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Questionário Social (S)", description = "Exclui um questionário social existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Questionário social excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Questionário social não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo questionário social com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Questionário social não encontrado")
        );
        repository.deleteById(id);
    }
}
