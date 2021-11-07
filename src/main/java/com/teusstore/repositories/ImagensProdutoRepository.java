package com.teusstore.repositories;

import com.teusstore.models.ImagensProduto;
import com.teusstore.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagensProdutoRepository extends JpaRepository<ImagensProduto, Long>{
    List<ImagensProduto> findImagensProdutoByProduto(Produto produto);
}
