package com.carloscipreste.gragas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloscipreste.gragas.enums.PedidoStatus;
import com.carloscipreste.gragas.model.pedido.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByStatus(PedidoStatus status);

    List<Pedido> findByFinishedAtBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Pedido> findByFinishedAtIsNotNullOrderByFinishedAtDesc();
}
