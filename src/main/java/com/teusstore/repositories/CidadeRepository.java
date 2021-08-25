package com.teusstore.repositories;

import com.teusstore.models.Cidade;
import com.teusstore.models.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

}
