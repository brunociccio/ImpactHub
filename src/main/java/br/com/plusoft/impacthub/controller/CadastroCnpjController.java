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

import br.com.plusoft.impacthub.assembler.CadastroCnpjModelAssembler;
import br.com.plusoft.impacthub.model.CadastroCnpj;
import br.com.plusoft.impacthub.repository.CadastroCnpjRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cadastroCnpj")
@Tag(name = "CadastroCnpj", description = "APIs relacionadas ao cadastro de CNPJs")
@Slf4j
public class CadastroCnpjController {

    @Autowired
    private CadastroCnpjRepository repository;

    @Autowired
    private CadastroCnpjModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<CadastroCnpj> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os CNPJs", description = "Retorna uma lista de todos os CNPJs com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de CNPJs retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<CadastroCnpj>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os CNPJs com paginação");
        Page<CadastroCnpj> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar CNPJ por ID", description = "Retorna um CNPJ específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CNPJ retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "CNPJ não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<CadastroCnpj> show(@PathVariable Long id) {
        log.info("Buscando CNPJ com id {}", id);
        var cadastroCnpj = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("CNPJ não encontrado")
        );
        return assembler.toModel(cadastroCnpj);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo CNPJ", description = "Cria um novo CNPJ com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CNPJ criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<CadastroCnpj>> create(@RequestBody @Valid CadastroCnpj cadastroCnpj) {
        log.info("Criando um novo CNPJ");
        repository.save(cadastroCnpj);
        var entityModel = assembler.toModel(cadastroCnpj);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um CNPJ", description = "Atualiza os dados de um CNPJ existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CNPJ atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "CNPJ não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<CadastroCnpj>> update(@PathVariable Long id, @RequestBody @Valid CadastroCnpj cadastroCnpj) {
        log.info("Atualizando CNPJ com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("CNPJ não encontrado")
        );
        cadastroCnpj.setIdEmpresa(id);
        repository.save(cadastroCnpj);
        return ResponseEntity.ok(assembler.toModel(cadastroCnpj));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um CNPJ", description = "Exclui um CNPJ existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "CNPJ excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "CNPJ não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo CNPJ com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("CNPJ não encontrado")
        );
        repository.deleteById(id);
    }
}
