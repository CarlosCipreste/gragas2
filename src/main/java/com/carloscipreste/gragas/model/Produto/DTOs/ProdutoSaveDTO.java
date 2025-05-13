package com.carloscipreste.gragas.model.Produto.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProdutoSaveDTO(
        @NotBlank String nome,
        @Size(max = 255) String descricao,
        @NotBlank String categoria,
        @Positive double preco,
        @Min(0) int quantidadeEstoque) {
}
