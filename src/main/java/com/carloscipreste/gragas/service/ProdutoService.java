package com.carloscipreste.gragas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carloscipreste.gragas.exceptions.ProdutoNaoEncontradoException;
import com.carloscipreste.gragas.model.Produto.Produto;
import com.carloscipreste.gragas.model.Produto.DTOs.ProdutoResponseDTO;
import com.carloscipreste.gragas.model.Produto.DTOs.ProdutoSaveDTO;
import com.carloscipreste.gragas.model.Produto.DTOs.ProdutoUpdateDTO;
import com.carloscipreste.gragas.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto salvarProduto(ProdutoSaveDTO dto) {
        Produto novoProduto = new Produto(dto);
        novoProduto.validar();
        return produtoRepository.save(novoProduto);
    }

    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        return ProdutoResponseDTO.from(produto);
    }

    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::from)
                .toList();
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(ProdutoUpdateDTO dto, Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        produto.atualizarComDTO(dto);
        produtoRepository.save(produto);

        return ProdutoResponseDTO.from(produto);
    }

}
