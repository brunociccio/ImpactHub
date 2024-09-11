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

import br.com.plusoft.impacthub.model.Cadastro;
import br.com.plusoft.impacthub.repository.CadastroRepository;
import br.com.plusoft.impacthub.assembler.CadastroModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cadastro")
@Tag(name = "Cadastro", description = "APIs relacionadas ao cadastro de usuários e empresas")
@Slf4j
public class CadastroController {

    @Autowired
    private CadastroRepository repository;

    @Autowired
    private CadastroModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Cadastro> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Cadastros", description = "Retorna uma lista de todos os cadastros com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de cadastros retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Cadastro>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os cadastros com paginação");
        Page<Cadastro> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Cadastro por ID", description = "Retorna um cadastro específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cadastro retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cadastro não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Cadastro> show(@PathVariable Long id) {
        log.info("Buscando cadastro com id {}", id);
        var cadastro = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Cadastro não encontrado")
        );
        return assembler.toModel(cadastro);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Cadastro", description = "Cria um novo cadastro com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cadastro criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Cadastro>> create(@RequestBody @Valid Cadastro cadastro) {
        log.info("Criando um novo cadastro");
        repository.save(cadastro);
        var entityModel = assembler.toModel(cadastro);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Cadastro", description = "Atualiza os dados de um cadastro existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cadastro atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cadastro não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Cadastro>> update(@PathVariable Long id, @RequestBody @Valid Cadastro cadastro) {
        log.info("Atualizando cadastro com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Cadastro não encontrado")
        );
        cadastro.setIdUsuario(id);
        repository.save(cadastro);
        return ResponseEntity.ok(assembler.toModel(cadastro));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Cadastro", description = "Exclui um cadastro existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cadastro excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cadastro não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo cadastro com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Cadastro não encontrado")
        );
        repository.deleteById(id);
    }
}
