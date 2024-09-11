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

import br.com.plusoft.impacthub.model.DocumentosCadastrais;
import br.com.plusoft.impacthub.repository.DocumentosCadastraisRepository;
import br.com.plusoft.impacthub.assembler.DocumentosCadastraisModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/documentosCadastrais")
@Tag(name = "DocumentosCadastrais", description = "APIs relacionadas ao gerenciamento de documentos cadastrais")
@Slf4j
public class DocumentosCadastraisController {

    @Autowired
    private DocumentosCadastraisRepository repository;

    @Autowired
    private DocumentosCadastraisModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<DocumentosCadastrais> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os Documentos Cadastrais", description = "Retorna uma lista de todos os documentos cadastrais com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de documentos cadastrais retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<DocumentosCadastrais>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os documentos cadastrais com paginação");
        Page<DocumentosCadastrais> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar Documento Cadastral por ID", description = "Retorna um documento cadastral específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento cadastral retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento cadastral não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<DocumentosCadastrais> show(@PathVariable Long id) {
        log.info("Buscando documento cadastral com id {}", id);
        var documentoCadastral = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento cadastral não encontrado")
        );
        return assembler.toModel(documentoCadastral);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo Documento Cadastral", description = "Cria um novo documento cadastral com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Documento cadastral criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<DocumentosCadastrais>> create(@RequestBody @Valid DocumentosCadastrais documentoCadastral) {
        log.info("Criando um novo documento cadastral");
        repository.save(documentoCadastral);
        var entityModel = assembler.toModel(documentoCadastral);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um Documento Cadastral", description = "Atualiza os dados de um documento cadastral existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento cadastral atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento cadastral não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<DocumentosCadastrais>> update(@PathVariable Long id, @RequestBody @Valid DocumentosCadastrais documentoCadastral) {
        log.info("Atualizando documento cadastral com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento cadastral não encontrado")
        );
        documentoCadastral.setId(id);
        repository.save(documentoCadastral);
        return ResponseEntity.ok(assembler.toModel(documentoCadastral));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um Documento Cadastral", description = "Exclui um documento cadastral existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Documento cadastral excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento cadastral não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo documento cadastral com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento cadastral não encontrado")
        );
        repository.deleteById(id);
    }
}
