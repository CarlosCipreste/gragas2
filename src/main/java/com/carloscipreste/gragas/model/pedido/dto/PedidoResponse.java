package com.carloscipreste.gragas.model.pedido.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.carloscipreste.gragas.model.itempedido.ItemPedido;
import com.carloscipreste.gragas.model.pedido.Pedido;

public record PedidoResponse(Long id, LocalDateTime data, BigDecimal valorTotal, List<ItemPedido> itens) {


    public static PedidoResponse from(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCreatedAt(),
                pedido.getValorTotal(),
                pedido.getItens());
    }
}
