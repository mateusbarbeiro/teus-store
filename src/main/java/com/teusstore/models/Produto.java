package com.teusstore.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product")
public class Produto implements Serializable {

    public Produto() {
        super();
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String descricao;

    private double valorVenda;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Marca marca;

    private  Double quantidadeEstoque = 0.;

    private String nomeImagem;

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Double getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Double quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}
