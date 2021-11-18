package com.teusstore.repositories;

import com.teusstore.models.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    @Query(value = "SELECT p FROM Produto p INNER JOIN Marca m ON p.marca.id = m.id INNER JOIN Categoria c ON p.categoria.id = c.id WHERE LOWER(p.descricao) LIKE %?1% OR LOWER(m.nome) LIKE %?1%  OR LOWER(c.nome) LIKE %?1%")
    Page<Produto> findAll(String filtro, Pageable pageable);
}
