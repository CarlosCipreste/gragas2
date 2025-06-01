package com.carloscipreste.gragas.model.itempedido;


import com.carloscipreste.gragas.model.pedido.Pedido;
import com.carloscipreste.gragas.model.produto.Produto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Produto produto;

    @Positive(message = "quantidade deve ser maior que zero")
    @NotNull
    private int quantidade;

    @ManyToOne
    @NotNull
    private Pedido pedido;

}
