package com.carloscipreste.gragas.service;

import java.util.List;

import com.carloscipreste.gragas.model.Produto;
import com.carloscipreste.gragas.repository.ProdutoRepository;

public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;

    public ProdutoService (ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

}
