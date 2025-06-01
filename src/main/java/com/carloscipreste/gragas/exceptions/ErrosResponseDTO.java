package com.carloscipreste.gragas.exceptions;

import java.time.LocalDateTime;
import java.util.List;


public record ErrosResponseDTO(
        int status,
        String erro,
        String caminho,
        LocalDateTime timestamp,
        List<ErroValidacaoDTO> erros) {

    public ErrosResponseDTO(int status, String erro, String caminho, List<ErroValidacaoDTO> erros) {
        this(status, erro, caminho, LocalDateTime.now(), erros);
    }
}
