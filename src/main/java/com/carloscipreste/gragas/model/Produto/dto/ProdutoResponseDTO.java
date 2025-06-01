package com.carloscipreste.gragas.model.produto.dto;

import java.math.BigDecimal;

import com.carloscipreste.gragas.enums.ProdutoStatus;
import com.carloscipreste.gragas.model.produto.Produto;

public record ProdutoResponseDTO(String nome, String descricao, String categoria, BigDecimal preco, int quantidadeEstoque,
        ProdutoStatus status) {

    public static ProdutoResponseDTO from(Produto produto) {

        return new ProdutoResponseDTO(produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.getQuantidadeEstoque(),
                produto.getStatus());
    }

}
