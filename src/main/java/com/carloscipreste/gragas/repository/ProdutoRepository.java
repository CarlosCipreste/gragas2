package com.carloscipreste.gragas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloscipreste.gragas.enums.ProdutoStatus;
import com.carloscipreste.gragas.model.produto.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    List<Produto> findByStatus(ProdutoStatus status);

}
