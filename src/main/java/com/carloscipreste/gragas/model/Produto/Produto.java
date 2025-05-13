package com.carloscipreste.gragas.model.Produto;

import com.carloscipreste.gragas.enums.ProdutoStatus;
import com.carloscipreste.gragas.model.Produto.DTOs.ProdutoSaveDTO;
import com.carloscipreste.gragas.model.Produto.DTOs.ProdutoUpdateDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String nome;

    @NotBlank
    private String descricao;

    @NotBlank
    private String categoria;

    @NotNull
    @Positive
    private double preco;

    @NotNull
    @Min(0)
    private int quantidadeEstoque;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProdutoStatus ativo = ProdutoStatus.DISPONIVEL;

    public void atualizarStatusEstoque() {
        if (quantidadeEstoque <= 0) {
            this.ativo = ProdutoStatus.ESGOTADO;
        } else {
            this.ativo = ProdutoStatus.DISPONIVEL;
        }
    }

    public void validar() {
        if (this.getPreco() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }

        if (this.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo.");
        }
    }

    public Produto(ProdutoSaveDTO fromDTO) {
        this.nome = fromDTO.nome();
        this.descricao = fromDTO.descricao();
        this.categoria = fromDTO.categoria();
        this.preco = fromDTO.preco();
        this.quantidadeEstoque = fromDTO.quantidadeEstoque();
    }

    public void atualizarComDTO(ProdutoUpdateDTO dto) {
        this.setNome(dto.nome());
        this.setDescricao(dto.descricao());
        this.setCategoria(dto.categoria());
        this.setPreco(dto.preco());
        this.setQuantidadeEstoque(dto.quantidadeEstoque());
        this.atualizarStatusEstoque();
    }
}
