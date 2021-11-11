package com.teusstore.repositories;

import com.teusstore.models.Categoria;
import com.teusstore.models.Marca;
import com.teusstore.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    List<Produto> findAllByDescricaoContains(String descricao);

    List<Produto> findAllByCategoria(Categoria categoria);

    List<Produto> findAllByMarca(Marca marca);

    List<Produto> findTopByOrderByIdAsc();
}
