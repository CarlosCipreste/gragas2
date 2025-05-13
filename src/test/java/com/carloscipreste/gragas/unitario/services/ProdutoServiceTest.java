package com.carloscipreste.gragas.unitario.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
import com.carloscipreste.gragas.exceptions.ProdutoNaoEncontradoException;
import com.carloscipreste.gragas.model.Produto.Produto;
import com.carloscipreste.gragas.model.Produto.DTOs.ProdutoResponseDTO;
import com.carloscipreste.gragas.repository.ProdutoRepository;
import com.carloscipreste.gragas.service.ProdutoService;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Deve retornar uma lista com um produto")
    public void deveRetornarUmaListaComUmUsuario() {

        Produto produtoMockado = new Produto(1L, "Petra", "American Lager", "Cerveja", 4.30, 24,
                ProdutoStatus.DISPONIVEL);
        Mockito.when(produtoRepository.findAll()).thenReturn(Collections.singletonList(produtoMockado));

        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        Assertions.assertEquals(1, produtos.size());

    }

    @Test
    @DisplayName("Deve Lançar uma exceção quando o produto não for encontrado")
    public void deveLancarExcecaoQuandoProdutoForNulo() {
        when(produtoRepository.findById(99999L)).thenReturn(Optional.empty());

        ProdutoNaoEncontradoException ex = assertThrows(
                ProdutoNaoEncontradoException.class,
                () -> produtoService.buscarProdutoPorId(99999L));

        Assertions.assertEquals("Produto com ID 99999 nao encontrado.", ex.getMessage());
    }

}
