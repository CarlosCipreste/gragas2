package com.carloscipreste.gragas.model.produto.dto;

import java.math.BigDecimal;

import com.carloscipreste.gragas.enums.ProdutoStatus;

import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProdutoUpdateDTO(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotBlank String categoria,
        @Positive BigDecimal preco,
        @Min(0) int quantidadeEstoque,
        @Nullable @Enumerated ProdutoStatus status) {

}
