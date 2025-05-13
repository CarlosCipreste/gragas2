package com.carloscipreste.gragas.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ProdutoNaoEncontradoException extends EntityNotFoundException {
    public ProdutoNaoEncontradoException(Long id) {
        super("Produto com ID " + id + " nao encontrado.");
    }
}
