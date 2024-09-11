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

import br.com.plusoft.impacthub.model.Resultado;
import br.com.plusoft.impacthub.repository.ResultadoRepository;
import br.com.plusoft.impacthub.assembler.ResultadoModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/resultado")
@Tag(name = "Resultado", description = "APIs relacionadas ao gerenciamento de resultados")
@Slf4j
public class ResultadoController {

    @Autowired
    private ResultadoRepository repository;

    @Autowired
    private ResultadoModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Resultado> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Resultados", description = "Retorna uma lista de todos os resultados com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de resultados retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Resultado>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os resultados com paginação");
        Page<Resultado> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Resultado por ID", description = "Retorna um resultado específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultado retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Resultado não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Resultado> show(@PathVariable Long id) {
        log.info("Buscando resultado com id {}", id);
        var resultado = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Resultado não encontrado")
        );
        return assembler.toModel(resultado);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Resultado", description = "Cria um novo resultado com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Resultado criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Resultado>> create(@RequestBody @Valid Resultado resultado) {
        log.info("Criando um novo resultado");
        repository.save(resultado);
        var entityModel = assembler.toModel(resultado);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Resultado", description = "Atualiza os dados de um resultado existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultado atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Resultado não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Resultado>> update(@PathVariable Long id, @RequestBody @Valid Resultado resultado) {
        log.info("Atualizando resultado com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Resultado não encontrado")
        );
        resultado.setIdResultado(id);
        repository.save(resultado);
        return ResponseEntity.ok(assembler.toModel(resultado));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Resultado", description = "Exclui um resultado existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Resultado excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Resultado não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo resultado com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Resultado não encontrado")
        );
        repository.deleteById(id);
    }
}
