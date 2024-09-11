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

import br.com.plusoft.impacthub.model.RankingEmpresas;
import br.com.plusoft.impacthub.repository.RankingEmpresasRepository;
import br.com.plusoft.impacthub.assembler.RankingEmpresasModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/rankingEmpresas")
@Tag(name = "RankingEmpresas", description = "APIs relacionadas ao gerenciamento de ranking de empresas")
@Slf4j
public class RankingEmpresasController {

    @Autowired
    private RankingEmpresasRepository repository;

    @Autowired
    private RankingEmpresasModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<RankingEmpresas> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Rankings de Empresas", description = "Retorna uma lista de todos os rankings de empresas com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de rankings de empresas retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<RankingEmpresas>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os rankings de empresas com paginação");
        Page<RankingEmpresas> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Ranking de Empresas por ID", description = "Retorna um ranking de empresas específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ranking de empresas retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ranking de empresas não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<RankingEmpresas> show(@PathVariable Long id) {
        log.info("Buscando ranking de empresas com id {}", id);
        var rankingEmpresas = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Ranking de empresas não encontrado")
        );
        return assembler.toModel(rankingEmpresas);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Ranking de Empresas", description = "Cria um novo ranking de empresas com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ranking de empresas criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<RankingEmpresas>> create(@RequestBody @Valid RankingEmpresas rankingEmpresas) {
        log.info("Criando um novo ranking de empresas");
        repository.save(rankingEmpresas);
        var entityModel = assembler.toModel(rankingEmpresas);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Ranking de Empresas", description = "Atualiza os dados de um ranking de empresas existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ranking de empresas atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ranking de empresas não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<RankingEmpresas>> update(@PathVariable Long id, @RequestBody @Valid RankingEmpresas rankingEmpresas) {
        log.info("Atualizando ranking de empresas com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Ranking de empresas não encontrado")
        );
        rankingEmpresas.setIdRanking(id);
        repository.save(rankingEmpresas);
        return ResponseEntity.ok(assembler.toModel(rankingEmpresas));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Ranking de Empresas", description = "Exclui um ranking de empresas existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ranking de empresas excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ranking de empresas não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo ranking de empresas com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Ranking de empresas não encontrado")
        );
        repository.deleteById(id);
    }
}
