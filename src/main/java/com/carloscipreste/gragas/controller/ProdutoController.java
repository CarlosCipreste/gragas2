package com.carloscipreste.gragas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.carloscipreste.gragas.model.produto.Produto;
import com.carloscipreste.gragas.model.produto.dto.ProdutoResponseDTO;
import com.carloscipreste.gragas.model.produto.dto.ProdutoSaveDTO;
import com.carloscipreste.gragas.model.produto.dto.ProdutoUpdateDTO;
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
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutosAtivos() {
        List<ProdutoResponseDTO> produtos = produtoService.listarProdutosAtivos();
        return ResponseEntity.ok().body(produtos);
    }

    @PostMapping
    ResponseEntity<Void> salvarProduto(@RequestBody @Valid ProdutoSaveDTO produto,  UriComponentsBuilder uriBuilder) {    
        Produto produtoSalvo = produtoService.salvarProduto(produto);
        var Uri = uriBuilder.path(produtoSalvo.getNome()).buildAndExpand(produtoSalvo.getId()).toUri();
        return ResponseEntity.created(Uri).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<ProdutoResponseDTO> atualizarProduto (@PathVariable Long id, @RequestBody @Valid ProdutoUpdateDTO produto ) {
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizarProduto(produto, id);
        return ResponseEntity.ok().body(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> desativarProduto(@PathVariable Long id){
        produtoService.desativaProduto(id);
        return ResponseEntity.noContent().build();
    }
    
}
