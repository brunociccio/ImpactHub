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

import br.com.plusoft.impacthub.assembler.ContatoModelAssembler;
import br.com.plusoft.impacthub.model.Contato;
import br.com.plusoft.impacthub.repository.ContatoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/contato")
@Tag(name = "Contato", description = "APIs relacionadas ao gerenciamento de contatos")
@Slf4j
public class ContatoController {

    @Autowired
    private ContatoRepository repository;

    @Autowired
    private ContatoModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Contato> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Contatos", description = "Retorna uma lista de todos os contatos com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de contatos retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Contato>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os contatos com paginação");
        Page<Contato> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Contato por ID", description = "Retorna um contato específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contato retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Contato> show(@PathVariable Long id) {
        log.info("Buscando contato com id {}", id);
        var contato = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Contato não encontrado")
        );
        return assembler.toModel(contato);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Contato", description = "Cria um novo contato com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Contato>> create(@RequestBody @Valid Contato contato) {
        log.info("Criando um novo contato");
        repository.save(contato);
        var entityModel = assembler.toModel(contato);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Contato", description = "Atualiza os dados de um contato existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Contato>> update(@PathVariable Long id, @RequestBody @Valid Contato contato) {
        log.info("Atualizando contato com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Contato não encontrado")
        );
        contato.setId(id);
        repository.save(contato);
        return ResponseEntity.ok(assembler.toModel(contato));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Contato", description = "Exclui um contato existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Contato excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contato não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo contato com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Contato não encontrado")
        );
        repository.deleteById(id);
    }
}
