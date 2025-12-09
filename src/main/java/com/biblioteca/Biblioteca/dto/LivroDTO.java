package com.biblioteca.Biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de resposta com dados completos do livro")
public class LivroDTO {
    
    @Schema(description = "Identificador único do livro", example = "1")
    private Long id;
    
    @Schema(description = "Título do livro", example = "Clean Code")
    private String titulo;
    
    @Schema(description = "Autor do livro", example = "Robert C. Martin")
    private String autor;
    
    @Schema(description = "Número ISBN do livro", example = "978-0132350884")
    private String isbn;
    
    @Schema(description = "Ano de publicação", example = "2008")
    private Integer anoPublicacao;
    
    @Schema(description = "Status de disponibilidade", example = "true")
    private Boolean disponivel;
}
