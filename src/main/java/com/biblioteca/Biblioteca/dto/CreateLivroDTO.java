package com.biblioteca.Biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de um novo livro")
public class CreateLivroDTO {

    @Schema(description = "Título do livro", example = "Clean Code")
    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @Schema(description = "Autor do livro", example = "Robert C. Martin")
    @NotBlank(message = "O autor é obrigatório")
    private String autor;

    @Schema(description = "Número ISBN único do livro", example = "978-0132350884")
    @NotBlank(message = "O ISBN é obrigatório")
    private String isbn;

    @Schema(description = "Ano de publicação do livro", example = "2008")
    @NotNull(message = "O ano de publicação é obrigatório")
    private Integer anoPublicacao;

    @Schema(description = "Status de disponibilidade (padrão: true)", example = "true")
    private Boolean disponivel = true;
}
