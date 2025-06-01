package com.carloscipreste.gragas.unitario.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.carloscipreste.gragas.enums.ProdutoStatus;
import com.carloscipreste.gragas.model.produto.Produto;
import com.carloscipreste.gragas.model.produto.dto.ProdutoResponseDTO;
import com.carloscipreste.gragas.repository.ProdutoRepository;
import com.carloscipreste.gragas.service.ProdutoService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Deve retornar uma lista com um produto")
    public void deveRetornarUmaListaComUmUsuario() {
        Produto produtoMockado = new Produto(1L, "Petra", "American Lager", "Cerveja", BigDecimal.valueOf(4.20), 24,
                ProdutoStatus.DISPONIVEL);
        Mockito.when(produtoRepository.findAll()).thenReturn(Collections.singletonList(produtoMockado));

        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        Assertions.assertEquals(1, produtos.size());

    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver produtos")
    public void deveRetornarListaVazia() {
        when(produtoRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        Assertions.assertTrue(true == produtos.isEmpty());
    }

    @Test
    @DisplayName("Deve Retornar um produto quando encontrado")
    public void deveRetornarUmProduto() {
        when(produtoRepository.findById(1L)).thenReturn(
                Optional.of(new Produto(1L, "Petra", "American Lager", "Cerveja", BigDecimal.valueOf(4.20), 24,
                        ProdutoStatus.DISPONIVEL)));
        ProdutoResponseDTO produto = produtoService.buscarProdutoPorId(1L);
        Assertions.assertEquals(produto, produto);
    }

    @Test
    @DisplayName("Deve Lançar uma exceção quando o produto não for encontrado")
    public void deveLancarExcecaoQuandoProdutoForNulo() {
        when(produtoRepository.findById(99999L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> produtoService.buscarProdutoPorId(99999L));

        Assertions.assertEquals("PRODUTO não encontrado no id: " + 99999L, ex.getMessage());
    }

}
