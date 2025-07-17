package com.perfulandia.cl.perfulandia.repository;

import com.perfulandia.cl.perfulandia.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
}
