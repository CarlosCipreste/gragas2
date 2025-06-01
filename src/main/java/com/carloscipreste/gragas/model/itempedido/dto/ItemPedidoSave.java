package com.carloscipreste.gragas.model.itempedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoSave(
        @NotNull(message = "Produto obrigatório") Long produtoId,

        @NotNull(message = "Quantidade obrigatória") @Min(value = 1, message = "Quantidade deve ser maior que zero") int quantidade) {

}
