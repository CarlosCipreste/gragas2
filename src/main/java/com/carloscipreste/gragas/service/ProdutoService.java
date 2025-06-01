package com.carloscipreste.gragas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.carloscipreste.gragas.enums.ProdutoStatus;
import com.carloscipreste.gragas.model.produto.Produto;
import com.carloscipreste.gragas.model.produto.dto.ProdutoResponseDTO;
import com.carloscipreste.gragas.model.produto.dto.ProdutoSaveDTO;
import com.carloscipreste.gragas.model.produto.dto.ProdutoUpdateDTO;
import com.carloscipreste.gragas.repository.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::from)
                .toList();
    }

    public List<ProdutoResponseDTO> listarProdutosAtivos() {
        return produtoRepository.findByStatus(ProdutoStatus.DISPONIVEL)
                .stream()
                .map(ProdutoResponseDTO::from)
                .toList();
    }

    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PRODUTO não encontrado no id: " + id));
        return ProdutoResponseDTO.from(produto);
    }

    public Produto salvarProduto(ProdutoSaveDTO dto) {
        Produto novoProduto = new Produto(dto);

        return produtoRepository.save(novoProduto);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(ProdutoUpdateDTO dto, Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PRODUTO não encontrado no id: " + id));

        produto.atualizarComDTO(dto);
        produtoRepository.save(produto);

        return ProdutoResponseDTO.from(produto);
    }

    @Transactional
    public void desativaProduto(Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("PRODUTO não encontrado no id: " + id));
        produto.desativar();
        produtoRepository.save(produto);
    }
}
