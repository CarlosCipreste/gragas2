package com.carloscipreste.gragas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErroValidacaoDTO {
    private String campo;
    private String mensagem;

}
