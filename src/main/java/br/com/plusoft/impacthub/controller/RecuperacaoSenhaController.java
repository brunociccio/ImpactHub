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

import br.com.plusoft.impacthub.assembler.RecuperacaoSenhaModelAssembler;
import br.com.plusoft.impacthub.model.RecuperacaoSenha;
import br.com.plusoft.impacthub.repository.RecuperacaoSenhaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recuperacaoSenha")
@Tag(name = "RecuperacaoSenha", description = "APIs relacionadas ao gerenciamento de recuperação de senha")
@Slf4j
public class RecuperacaoSenhaController {

    @Autowired
    private RecuperacaoSenhaRepository repository;

    @Autowired
    private RecuperacaoSenhaModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<RecuperacaoSenha> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todas as Recuperações de Senha", description = "Retorna uma lista de todas as recuperações de senha com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de recuperações de senha retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<RecuperacaoSenha>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todas as recuperações de senha com paginação");
        Page<RecuperacaoSenha> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Recuperação de Senha por ID", description = "Retorna uma recuperação de senha específica pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recuperação de senha retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Recuperação de senha não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<RecuperacaoSenha> show(@PathVariable Long id) {
        log.info("Buscando recuperação de senha com id {}", id);
        var recuperacaoSenha = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Recuperação de senha não encontrada")
        );
        return assembler.toModel(recuperacaoSenha);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar uma nova Recuperação de Senha", description = "Cria uma nova recuperação de senha com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Recuperação de senha criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<RecuperacaoSenha>> create(@RequestBody @Valid RecuperacaoSenha recuperacaoSenha) {
        log.info("Criando uma nova recuperação de senha");
        repository.save(recuperacaoSenha);
        var entityModel = assembler.toModel(recuperacaoSenha);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar uma Recuperação de Senha", description = "Atualiza os dados de uma recuperação de senha existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recuperação de senha atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Recuperação de senha não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<RecuperacaoSenha>> update(@PathVariable Long id, @RequestBody @Valid RecuperacaoSenha recuperacaoSenha) {
        log.info("Atualizando recuperação de senha com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Recuperação de senha não encontrada")
        );
        recuperacaoSenha.setId(id);
        repository.save(recuperacaoSenha);
        return ResponseEntity.ok(assembler.toModel(recuperacaoSenha));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir uma Recuperação de Senha", description = "Exclui uma recuperação de senha existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Recuperação de senha excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Recuperação de senha não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo recuperação de senha com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Recuperação de senha não encontrada")
        );
        repository.deleteById(id);
    }
}
