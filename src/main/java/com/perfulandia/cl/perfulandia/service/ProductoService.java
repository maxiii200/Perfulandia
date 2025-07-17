package com.perfulandia.cl.perfulandia.service;

import com.perfulandia.cl.perfulandia.model.Producto;
import com.perfulandia.cl.perfulandia.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> obtenerTodos() {
        return repository.findAll();
    }

    public Producto obtenerPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}

