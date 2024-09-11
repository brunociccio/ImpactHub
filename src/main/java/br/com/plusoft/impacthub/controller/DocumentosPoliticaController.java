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

import br.com.plusoft.impacthub.model.DocumentosPolitica;
import br.com.plusoft.impacthub.repository.DocumentosPoliticaRepository;
import br.com.plusoft.impacthub.assembler.DocumentosPoliticaModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/documentosPolitica")
@Tag(name = "DocumentosPolitica", description = "APIs relacionadas ao gerenciamento de documentos de política")
@Slf4j
public class DocumentosPoliticaController {

    @Autowired
    private DocumentosPoliticaRepository repository;

    @Autowired
    private DocumentosPoliticaModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<DocumentosPolitica> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Documentos de Política", description = "Retorna uma lista de todos os documentos de política com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de documentos de política retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<DocumentosPolitica>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os documentos de política com paginação");
        Page<DocumentosPolitica> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Documento de Política por ID", description = "Retorna um documento de política específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento de política retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento de política não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<DocumentosPolitica> show(@PathVariable Long id) {
        log.info("Buscando documento de política com id {}", id);
        var documentoPolitica = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento de política não encontrado")
        );
        return assembler.toModel(documentoPolitica);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Documento de Política", description = "Cria um novo documento de política com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Documento de política criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<DocumentosPolitica>> create(@RequestBody @Valid DocumentosPolitica documentoPolitica) {
        log.info("Criando um novo documento de política");
        repository.save(documentoPolitica);
        var entityModel = assembler.toModel(documentoPolitica);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Documento de Política", description = "Atualiza os dados de um documento de política existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento de política atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento de política não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<DocumentosPolitica>> update(@PathVariable Long id, @RequestBody @Valid DocumentosPolitica documentoPolitica) {
        log.info("Atualizando documento de política com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento de política não encontrado")
        );
        documentoPolitica.setId(id);
        repository.save(documentoPolitica);
        return ResponseEntity.ok(assembler.toModel(documentoPolitica));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Documento de Política", description = "Exclui um documento de política existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Documento de política excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento de política não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo documento de política com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento de política não encontrado")
        );
        repository.deleteById(id);
    }
}
