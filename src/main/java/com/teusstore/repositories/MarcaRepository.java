package com.teusstore.repositories;

import com.teusstore.models.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long>{

}
