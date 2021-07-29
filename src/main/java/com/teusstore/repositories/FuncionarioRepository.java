package com.teusstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teusstore.models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
