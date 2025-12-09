package com.biblioteca.Biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para atualização de um livro (todos os campos são opcionais)")
public class UpdateLivroDTO {
    
    @Schema(description = "Título do livro", example = "Clean Code - Edição Atualizada")
    private String titulo;
    
    @Schema(description = "Autor do livro", example = "Robert C. Martin")
    private String autor;
    
    @Schema(description = "Número ISBN do livro", example = "978-0132350884")
    private String isbn;
    
    @Schema(description = "Ano de publicação", example = "2008")
    private Integer anoPublicacao;
    
    @Schema(description = "Status de disponibilidade", example = "false")
    private Boolean disponivel;
}
