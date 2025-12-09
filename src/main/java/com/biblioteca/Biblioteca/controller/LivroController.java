package com.biblioteca.Biblioteca.controller;

import com.biblioteca.Biblioteca.dto.CreateLivroDTO;
import com.biblioteca.Biblioteca.dto.LivroDTO;
import com.biblioteca.Biblioteca.dto.UpdateLivroDTO;
import com.biblioteca.Biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
@Tag(name = "Livros", description = "API para gerenciamento de livros da biblioteca")
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    @Operation(summary = "Criar um novo livro", description = "Cadastra um novo livro no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
                content = @Content(schema = @Schema(implementation = LivroDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou ISBN duplicado",
                content = @Content)
    })
    public ResponseEntity<LivroDTO> criarLivro(@Valid @RequestBody CreateLivroDTO createLivroDTO) {
        LivroDTO livroDTO = livroService.criarLivro(createLivroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros", description = "Retorna a lista completa de livros cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    public ResponseEntity<List<LivroDTO>> listarTodos() {
        List<LivroDTO> livros = livroService.listarTodos();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID", description = "Retorna os detalhes de um livro específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado",
                content = @Content(schema = @Schema(implementation = LivroDTO.class))),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                content = @Content)
    })
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        LivroDTO livroDTO = livroService.buscarPorId(id);
        return ResponseEntity.ok(livroDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar livro", description = "Atualiza as informações de um livro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = LivroDTO.class))),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                content = @Content),
        @ApiResponse(responseCode = "400", description = "ISBN duplicado",
                content = @Content)
    })
    public ResponseEntity<LivroDTO> atualizarLivro(
            @PathVariable Long id,
            @RequestBody UpdateLivroDTO updateLivroDTO) {
        LivroDTO livroDTO = livroService.atualizarLivro(id, updateLivroDTO);
        return ResponseEntity.ok(livroDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir livro", description = "Remove um livro do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                content = @Content)
    })
    public ResponseEntity<Void> excluirLivro(@PathVariable Long id) {
        livroService.excluirLivro(id);
        return ResponseEntity.noContent().build();
    }
}
