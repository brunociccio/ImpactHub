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

import br.com.plusoft.impacthub.model.DocumentosAmbientais;
import br.com.plusoft.impacthub.repository.DocumentosAmbientaisRepository;
import br.com.plusoft.impacthub.assembler.DocumentosAmbientaisModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/documentosAmbientais")
@Tag(name = "DocumentosAmbientais", description = "APIs relacionadas ao gerenciamento de documentos ambientais")
@Slf4j
public class DocumentosAmbientaisController {

    @Autowired
    private DocumentosAmbientaisRepository repository;

    @Autowired
    private DocumentosAmbientaisModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<DocumentosAmbientais> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Documentos Ambientais", description = "Retorna uma lista de todos os documentos ambientais com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de documentos ambientais retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<DocumentosAmbientais>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os documentos ambientais com paginação");
        Page<DocumentosAmbientais> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Documento Ambiental por ID", description = "Retorna um documento ambiental específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento ambiental retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento ambiental não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<DocumentosAmbientais> show(@PathVariable Long id) {
        log.info("Buscando documento ambiental com id {}", id);
        var documentoAmbiental = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento ambiental não encontrado")
        );
        return assembler.toModel(documentoAmbiental);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Documento Ambiental", description = "Cria um novo documento ambiental com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Documento ambiental criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<DocumentosAmbientais>> create(@RequestBody @Valid DocumentosAmbientais documentoAmbiental) {
        log.info("Criando um novo documento ambiental");
        repository.save(documentoAmbiental);
        var entityModel = assembler.toModel(documentoAmbiental);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Documento Ambiental", description = "Atualiza os dados de um documento ambiental existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento ambiental atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento ambiental não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<DocumentosAmbientais>> update(@PathVariable Long id, @RequestBody @Valid DocumentosAmbientais documentoAmbiental) {
        log.info("Atualizando documento ambiental com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento ambiental não encontrado")
        );
        documentoAmbiental.setId(id);
        repository.save(documentoAmbiental);
        return ResponseEntity.ok(assembler.toModel(documentoAmbiental));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Documento Ambiental", description = "Exclui um documento ambiental existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Documento ambiental excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento ambiental não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo documento ambiental com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento ambiental não encontrado")
        );
        repository.deleteById(id);
    }
}
