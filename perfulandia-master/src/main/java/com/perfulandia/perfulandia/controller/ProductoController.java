package com.perfulandia.perfulandia.controller;


import com.perfulandia.perfulandia.model.Producto;
import com.perfulandia.perfulandia.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service){ this.service = service; }

    @GetMapping
    public List<Producto> listar() {return service.obtenerTodos();}

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {return service.obtenerPorId(id);}

    @GetMapping
    public Producto crear(@RequestBody Producto producto) {return service.guardar(producto);}

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.eliminar(id);}

}
