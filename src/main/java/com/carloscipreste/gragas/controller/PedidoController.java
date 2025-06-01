package com.carloscipreste.gragas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.carloscipreste.gragas.enums.PedidoStatus;
import com.carloscipreste.gragas.model.pedido.Pedido;
import com.carloscipreste.gragas.model.pedido.dto.PedidoRequest;
import com.carloscipreste.gragas.model.pedido.dto.PedidoResponse;
import com.carloscipreste.gragas.model.pedido.dto.PedidoSave;
import com.carloscipreste.gragas.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarPedidos(@RequestParam PedidoStatus status) {
        List<PedidoResponse> pedidos = pedidoService.listarPedidosByStatus(status);
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    ResponseEntity<PedidoRequest> criarPedido(@RequestBody @Valid PedidoSave pedido, UriComponentsBuilder uriBuilder) {
        Pedido pedidoSalvo = pedidoService.salvarPedido(pedido);
        var uri = uriBuilder.path("/{id}").buildAndExpand(pedidoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarPedido(@PathVariable Long id) {
        pedidoService.finalizarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
