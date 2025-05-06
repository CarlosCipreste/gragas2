package com.carloscipreste.gragas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carloscipreste.gragas.model.Produto;
import com.carloscipreste.gragas.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }   

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok().body(produtos);
    }

    @PostMapping ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) {
        Produto produtoSalvo = produtoService.salvarProduto(produto);
        return ResponseEntity.ok().body(produtoSalvo);
    }

}
