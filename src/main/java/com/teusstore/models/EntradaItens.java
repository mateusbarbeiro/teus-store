package com.teusstore.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "product_input_itens")
public class EntradaItens implements Serializable {

    public EntradaItens() {
        super();
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private EntradaProduto entradaProduto;
    @ManyToOne
    private Produto produto;

    private Double quantidade = 0.;

    private double valorProduto = 0.;

    private double valorVenda = 0.;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntradaProduto getEntradaProduto() {
        return entradaProduto;
    }

    public void setEntradaProduto(EntradaProduto entradaProduto) {
        this.entradaProduto = entradaProduto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }
}
