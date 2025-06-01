package com.carloscipreste.gragas.model.pedido.dto;

import java.util.List;

import com.carloscipreste.gragas.model.itempedido.dto.ItemPedidoSave;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PedidoSave(    
    @NotNull(message = "É necessário informar os itens do pedido")
    @NotEmpty(message = "O pedido deve conter pelo menos um item")
    List<ItemPedidoSave> itens
) {

}
