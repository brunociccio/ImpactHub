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

import br.com.plusoft.impacthub.model.Relatorio;
import br.com.plusoft.impacthub.repository.RelatorioRepository;
import br.com.plusoft.impacthub.assembler.RelatorioModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/relatorio")
@Tag(name = "Relatorio", description = "APIs relacionadas ao gerenciamento de relatórios")
@Slf4j
public class RelatorioController {

    @Autowired
    private RelatorioRepository repository;

    @Autowired
    private RelatorioModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Relatorio> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Relatórios", description = "Retorna uma lista de todos os relatórios com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de relatórios retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Relatorio>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os relatórios com paginação");
        Page<Relatorio> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Relatório por ID", description = "Retorna um relatório específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Relatorio> show(@PathVariable Long id) {
        log.info("Buscando relatório com id {}", id);
        var relatorio = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Relatório não encontrado")
        );
        return assembler.toModel(relatorio);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Relatório", description = "Cria um novo relatório com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Relatório criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Relatorio>> create(@RequestBody @Valid Relatorio relatorio) {
        log.info("Criando um novo relatório");
        repository.save(relatorio);
        var entityModel = assembler.toModel(relatorio);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Relatório", description = "Atualiza os dados de um relatório existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Relatorio>> update(@PathVariable Long id, @RequestBody @Valid Relatorio relatorio) {
        log.info("Atualizando relatório com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Relatório não encontrado")
        );
        relatorio.setIdRelatorio(id);
        repository.save(relatorio);
        return ResponseEntity.ok(assembler.toModel(relatorio));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Relatório", description = "Exclui um relatório existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Relatório excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo relatório com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Relatório não encontrado")
        );
        repository.deleteById(id);
    }
}
