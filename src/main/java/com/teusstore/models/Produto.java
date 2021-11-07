package com.teusstore.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(targetEntity=ImagensProduto.class, mappedBy="produto",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagensProduto> imagens = new ArrayList<>();

    public List<ImagensProduto> getImagens() {
        return imagens;
    }
    public void setImagens(List<ImagensProduto> imagens) {
        this.imagens = imagens;
    }


    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Marca marca;

    private  Double quantidadeEstoque = 0.;

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
