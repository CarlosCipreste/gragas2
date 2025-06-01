package com.carloscipreste.gragas.model.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.carloscipreste.gragas.enums.PedidoStatus;
import com.carloscipreste.gragas.model.itempedido.ItemPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "valor_total")
    @Positive(message = "Valor total deve ser maior que zero")
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    @Column(name = "created_at", insertable = false, updatable = false)
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Enumerated(EnumType.STRING)
    private PedidoStatus status;

    public void atualizarPedidoStatus(PedidoStatus status) {
        if (this.status != PedidoStatus.PENDENTE) {
            throw new IllegalStateException("Pedido não pode ser finalizado");
        }
        this.status = status;
        this.finishedAt = LocalDateTime.now();
    }

}