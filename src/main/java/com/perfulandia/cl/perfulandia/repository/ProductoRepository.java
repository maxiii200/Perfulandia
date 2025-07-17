package com.perfulandia.cl.perfulandia.repository;

import com.perfulandia.cl.perfulandia.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}

