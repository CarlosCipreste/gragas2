package com.carloscipreste.gragas.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.carloscipreste.gragas.enums.PedidoStatus;
import com.carloscipreste.gragas.model.itempedido.ItemPedido;
import com.carloscipreste.gragas.model.pedido.Pedido;
import com.carloscipreste.gragas.model.pedido.dto.PedidoResponse;
import com.carloscipreste.gragas.model.pedido.dto.PedidoSave;
import com.carloscipreste.gragas.model.produto.Produto;
import com.carloscipreste.gragas.repository.PedidoRepository;
import com.carloscipreste.gragas.repository.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final Validator validator;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository,Validator validator) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.validator = validator;
    }

    public List<PedidoResponse> listarPedidosByStatus(PedidoStatus status) {
        return pedidoRepository.findByStatus(status).stream()
                .map(PedidoResponse::from)
                .toList();
    }

    public Pedido salvarPedido(PedidoSave dto) {
        List<ItemPedido> itens = dto.itens().stream()
                .map(itemDto -> {
                    Produto produto = produtoRepository.findById(itemDto.produtoId())
                            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

                    ItemPedido item = new ItemPedido();
                    item.setProduto(produto);
                    item.setQuantidade(itemDto.quantidade());
                    return item;
                })
                .toList();

        BigDecimal valorTotal = itens.stream()
                .map(item -> item.getProduto().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Pedido pedido = new Pedido();
        pedido.setItens(itens);
        pedido.setValorTotal(valorTotal);
        pedido.setStatus(PedidoStatus.PENDENTE);
        pedido.setCreatedAt(LocalDateTime.now());
        itens.forEach(item -> item.setPedido(pedido)); // vínculo bidirecional

        Set<ConstraintViolation<Pedido>> violations = validator.validate(pedido);
        if(!violations.isEmpty()) throw new ConstraintViolationException(violations);
        return pedidoRepository.save(pedido);
    }

    public void finalizarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        pedido.atualizarPedidoStatus(PedidoStatus.FINALIZADO);
        pedidoRepository.save(pedido);
    }

    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        pedido.atualizarPedidoStatus(PedidoStatus.CANCELADO);
        pedidoRepository.save(pedido);
    }
}
