package com.carloscipreste.gragas.unitario.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.carloscipreste.gragas.controller.PedidoController;
import com.carloscipreste.gragas.enums.PedidoStatus;
import com.carloscipreste.gragas.exceptions.GlobalExceptionHandler;
import com.carloscipreste.gragas.model.pedido.Pedido;
import com.carloscipreste.gragas.model.pedido.dto.PedidoResponse;
import com.carloscipreste.gragas.model.pedido.dto.PedidoSave;
import com.carloscipreste.gragas.service.PedidoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
@Import(GlobalExceptionHandler.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    @Test
    @DisplayName("Deve retornar uma list de pedidos pendentes")
    void deveListarPedidosPendentes() throws Exception {

        List<Pedido> pedidos = List.of(new Pedido(1L, BigDecimal.valueOf(20.00), null, LocalDateTime.now(), null, PedidoStatus.PENDENTE));
        List<PedidoResponse> pedidoResponses = pedidos.stream().map(PedidoResponse::from).toList();
        when(pedidoService.listarPedidosByStatus(PedidoStatus.PENDENTE))
                .thenReturn(pedidoResponses);

        mockMvc.perform(get("/pedidos?status=PENDENTE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Deve retornar 201 Created ao criar um pedido válido")
    void deveRetonar201AoCriarPedido() throws Exception {
        String request = """
                {
                    "itens": [
                        {
                            "produtoId": 1,
                            "quantidade": 2
                        }
                    ]
                }
                """;

        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setId(1L);
        pedidoSalvo.setStatus(PedidoStatus.PENDENTE);
        pedidoSalvo.setCreatedAt(LocalDateTime.now());
        pedidoSalvo.setValorTotal(BigDecimal.valueOf(100));

        when(pedidoService.salvarPedido(any(PedidoSave.class))).thenReturn(pedidoSalvo);

        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Nested
    @DisplayName("Cenários de pedido inválido")
    class PedidoInvalidoTests {

        @Test
        @DisplayName("Deve retornar 400 se valor total for negativo")
        void deveRetornar400ValorTotalNegativo() throws Exception {
            testarPedidoInvalido("""
                    {
                        "itens": [
                            {
                                "produtoId": 1,
                                "quantidade": 0
                            }
                        ]
                    }
                    """, "Valor total deve ser maior que zero");
        }

        @Test
        @DisplayName("Deve retornar 400 se produtoId for nulo")
        void deveRetornar400ProdutoIdNulo() throws Exception {
            testarPedidoInvalido("""
                        {
                            "itens": [
                                {
                                    "produtoId": null,
                                    "quantidade": 1
                                }
                            ]
                        }
                    """, "The given id must not be null");
        }

        @Test
        @DisplayName("Deve retornar 400 se lista de itens estiver vazia")
        void deveRetornar400ItensVazios() throws Exception {
            testarPedidoInvalido("""
                        {
                            "itens": []
                        }
                    """, "O pedido deve conter pelo menos um item");
        }

        
    @Test
    @DisplayName("2 + 2 deve ser igual a 4")
    public void testSomaSimples() {
        int resultado = 2 + 2;

        assertEquals(4, resultado); // Compara o resultado esperado com o resultado real
    }

        private void testarPedidoInvalido(String requestJson, String mensagemEsperada) throws Exception {
            when(pedidoService.salvarPedido(any(PedidoSave.class)))
                    .thenThrow(new IllegalArgumentException(mensagemEsperada));

            mockMvc.perform(post("/pedidos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.erros.[0].mensagem").value(mensagemEsperada));
        }
    }
}
