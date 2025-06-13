package com.perfulandia.perfulandia.repository;

import com.perfulandia.perfulandia.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<usuario, Long> {

}
