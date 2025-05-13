package com.carloscipreste.gragas.model.Produto.DTOs;

import com.carloscipreste.gragas.enums.ProdutoStatus;
import com.carloscipreste.gragas.model.Produto.Produto;

public record ProdutoResponseDTO(String nome, String descricao, String categoria, double preco, int quantidadeEstoque,
        ProdutoStatus isAtivo) {

    public static ProdutoResponseDTO from(Produto produto) {

        return new ProdutoResponseDTO(produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.getQuantidadeEstoque(),
                produto.getAtivo());
    }

}
