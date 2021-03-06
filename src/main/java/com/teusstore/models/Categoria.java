package com.teusstore.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "category")
public class Categoria implements Serializable {

    public Categoria() {
        super();
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }
}
