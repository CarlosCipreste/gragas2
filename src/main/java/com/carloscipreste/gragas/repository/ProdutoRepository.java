package com.carloscipreste.gragas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carloscipreste.gragas.model.Produto.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
}
