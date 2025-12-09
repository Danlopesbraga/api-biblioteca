package com.biblioteca.Biblioteca.config;

import com.biblioteca.Biblioteca.entity.Livro;
import com.biblioteca.Biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// a IA cantou aqui, pq não sou doido de fazer mais de 100 inserts manualmente.
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private final LivroRepository livroRepository;

    private static final String[] GENEROS = {
            "Ficção Científica", "Romance", "Mistério", "Aventura", "Fantasia",
            "Terror", "Drama", "Comédia", "Biografia", "História",
            "Filosofia", "Poesia", "Suspense", "Thriller", "Distopia"
    };

    private static final String[] AUTORES = {
            "Machado de Assis", "Clarice Lispector", "Jorge Amado", "Carlos Drummond de Andrade",
            "Cecília Meireles", "Graciliano Ramos", "Rachel de Queiroz", "Monteiro Lobato",
            "Paulo Coelho", "Lygia Fagundes Telles", "Guimarães Rosa", "Érico Veríssimo",
            "José Saramago", "Gabriel García Márquez", "Isabel Allende", "Mario Vargas Llosa",
            "J.K. Rowling", "George Orwell", "Aldous Huxley", "Ray Bradbury",
            "Isaac Asimov", "Arthur C. Clarke", "Philip K. Dick", "Stephen King",
            "Agatha Christie", "Arthur Conan Doyle", "Edgar Allan Poe", "H.P. Lovecraft",
            "Tolkien", "C.S. Lewis", "Neil Gaiman", "Terry Pratchett",
            "Jane Austen", "Charles Dickens", "Victor Hugo", "Fiódor Dostoiévski",
            "Lev Tolstói", "Franz Kafka", "Ernest Hemingway", "F. Scott Fitzgerald",
            "Virginia Woolf", "James Joyce", "William Shakespeare", "Homer",
            "Sófocles", "Platão", "Aristóteles", "Dante Alighieri",
            "Miguel de Cervantes", "Goethe", "Friedrich Nietzsche", "Albert Camus"
    };

    private static final String[] PREFIXOS = {
            "O", "A", "Os", "As", "Um", "Uma", "Uns", "Umas",
            "O Grande", "A Pequena", "O Último", "A Primeira",
            "O Segredo de", "O Mistério de", "A História de", "A Lenda de",
            "Crônicas de", "Memórias de", "Aventuras de", "O Destino de"
    };

    private static final String[] SUBSTANTIVOS = {
            "Livro", "Noite", "Dia", "Sonho", "Cidade", "Reino", "Floresta", "Mar",
            "Montanha", "Rio", "Lago", "Castelo", "Torre", "Portal", "Espelho",
            "Chave", "Caminho", "Jornada", "Destino", "Tempo", "Mundo", "Universo",
            "Estrela", "Lua", "Sol", "Vento", "Tempestade", "Silêncio", "Sombra",
            "Luz", "Fogo", "Gelo", "Dragão", "Fênix", "Lobo", "Corvo", "Serpente",
            "Espada", "Escudo", "Coroa", "Trono", "Reino", "Império", "Terra",
            "Céu", "Inferno", "Paraíso", "Jardim", "Deserto", "Oásis", "Vale"
    };

    private static final String[] ADJETIVOS = {
            "Mágico", "Perdido", "Esquecido", "Proibido", "Sagrado", "Maldito",
            "Eterno", "Infinito", "Dourado", "Prateado", "Sombrio", "Brilhante",
            "Antigo", "Moderno", "Secreto", "Revelado", "Oculto", "Visível",
            "Poderoso", "Fraco", "Grande", "Pequeno", "Nobre", "Simples"
    };

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            long count = livroRepository.count();
            
            if (count > 0) {
                log.info("Banco de dados já contém {} livros. Pulando inserção de dados.", count);
                return;
            }

            log.info("Iniciando inserção de 10.000 livros no banco de dados...");
            
            List<Livro> livros = new ArrayList<>();
            Random random = new Random();
            
            for (int i = 1; i <= 10000; i++) {
                Livro livro = new Livro();
                
                String prefixo = PREFIXOS[random.nextInt(PREFIXOS.length)];
                String substantivo = SUBSTANTIVOS[random.nextInt(SUBSTANTIVOS.length)];
                String adjetivo = ADJETIVOS[random.nextInt(ADJETIVOS.length)];
                livro.setTitulo(prefixo + " " + substantivo + " " + adjetivo + " - Vol " + i);
                
                livro.setAutor(AUTORES[random.nextInt(AUTORES.length)]);
                
                livro.setIsbn(String.format("978-%d-%05d-%03d-%d", 
                    random.nextInt(10), 
                    i, 
                    random.nextInt(1000), 
                    random.nextInt(10)));
                
                livro.setAnoPublicacao(1950 + random.nextInt(75));
                
                livro.setDisponivel(random.nextInt(100) < 80);
                
                livros.add(livro);
                
                if (i % 1000 == 0) {
                    livroRepository.saveAll(livros);
                    livros.clear();
                    log.info("Inseridos {} livros...", i);
                }
            }
            
            if (!livros.isEmpty()) {
                livroRepository.saveAll(livros);
            }
            
            log.info("Inserção concluída! Total de livros no banco: {}", livroRepository.count());
        };
    }
}
