package com.carloscipreste.gragas.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrosResponseDTO> handleProdutoNaoEncontrado(EntityNotFoundException ex,
            HttpServletRequest request) {
        ErroValidacaoDTO erro = new ErroValidacaoDTO("recurso", ex.getMessage());
        ErrosResponseDTO response = new ErrosResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                request.getRequestURI(),
                List.of(erro));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrosResponseDTO> handleIllegalArgs(IllegalArgumentException ex, HttpServletRequest request) {
        ErroValidacaoDTO erro = new ErroValidacaoDTO("dados", ex.getMessage());
        ErrosResponseDTO response = new ErrosResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida",
                request.getRequestURI(),
                List.of(erro));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrosResponseDTO> handleConstraintViolation(ConstraintViolationException ex,
            HttpServletRequest request) {
        List<ErroValidacaoDTO> erros = ex.getConstraintViolations().stream()
                .map(cv -> new ErroValidacaoDTO(cv.getPropertyPath().toString(), cv.getMessage()))
                .toList();

        return ResponseEntity.badRequest().body(
                new ErrosResponseDTO(400, "Violação de restrições", request.getRequestURI(), erros));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrosResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<ErroValidacaoDTO> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> new ErroValidacaoDTO(field.getField(), field.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(
                new ErrosResponseDTO(400, "Erro de validação", request.getRequestURI(), erros));
    }
}
