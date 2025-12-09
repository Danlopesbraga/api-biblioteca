package com.biblioteca.Biblioteca.service;

import com.biblioteca.Biblioteca.dto.CreateLivroDTO;
import com.biblioteca.Biblioteca.dto.LivroDTO;
import com.biblioteca.Biblioteca.dto.UpdateLivroDTO;
import com.biblioteca.Biblioteca.entity.Livro;
import com.biblioteca.Biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public LivroDTO criarLivro(CreateLivroDTO createLivroDTO) {
        if (livroRepository.existsByIsbn(createLivroDTO.getIsbn())) {
            throw new IllegalArgumentException("Já existe um livro cadastrado com este ISBN");
        }

        Livro livro = modelMapper.map(createLivroDTO, Livro.class);
        if (livro.getDisponivel() == null) {
            livro.setDisponivel(true);
        }

        Livro livroSalvo = livroRepository.save(livro);
        return modelMapper.map(livroSalvo, LivroDTO.class);
    }

    @Transactional(readOnly = true)
    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll()
                .stream()
                .map(livro -> modelMapper.map(livro, LivroDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID: " + id));
        return modelMapper.map(livro, LivroDTO.class);
    }

    @Transactional
    public LivroDTO atualizarLivro(Long id, UpdateLivroDTO updateLivroDTO) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID: " + id));

        if (updateLivroDTO.getIsbn() != null && 
            !livro.getIsbn().equals(updateLivroDTO.getIsbn()) && 
            livroRepository.existsByIsbn(updateLivroDTO.getIsbn())) {
            throw new IllegalArgumentException("Já existe um livro cadastrado com este ISBN");
        }

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updateLivroDTO, livro);

        Livro livroAtualizado = livroRepository.save(livro);
        return modelMapper.map(livroAtualizado, LivroDTO.class);
    }

    @Transactional
    public void excluirLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado com o ID: " + id);
        }
        livroRepository.deleteById(id);
    }
}
