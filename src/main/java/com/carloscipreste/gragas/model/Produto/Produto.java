package com.carloscipreste.gragas.model.produto;

import java.math.BigDecimal;

import com.carloscipreste.gragas.enums.ProdutoStatus;
import com.carloscipreste.gragas.model.produto.dto.ProdutoSaveDTO;
import com.carloscipreste.gragas.model.produto.dto.ProdutoUpdateDTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    @NotBlank
    @Column(nullable = false)
    private String categoria;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal preco;

    @NotNull
    @Min(0)
    @Column(name = "quantidade_estoque", nullable = false)
    private int quantidadeEstoque;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProdutoStatus status;

    public void atualizarStatusEstoque() {
        if (quantidadeEstoque <= 0) {
            this.status = ProdutoStatus.ESGOTADO;
        } else {
            this.status = ProdutoStatus.DISPONIVEL;
        }
    }

    public void desativar() {
        this.status = ProdutoStatus.ESGOTADO;
    }

    public Produto(ProdutoSaveDTO fromDTO) {
        this.nome = fromDTO.nome();
        this.descricao = fromDTO.descricao();
        this.categoria = fromDTO.categoria();
        this.preco = fromDTO.preco();
        this.quantidadeEstoque = fromDTO.quantidadeEstoque();
        this.status = ProdutoStatus.DISPONIVEL;
    }

    public void atualizarComDTO(ProdutoUpdateDTO dto) {
        this.nome = dto.nome();
        this.descricao = dto.descricao();
        this.categoria = dto.categoria();
        this.preco = dto.preco();
        this.quantidadeEstoque = dto.quantidadeEstoque();
        this.atualizarStatusEstoque();
    }
}
