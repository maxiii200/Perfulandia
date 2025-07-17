package com.perfulandia.cl.perfulandia.controller;

import com.perfulandia.cl.perfulandia.assembler.ProductoModelAssembler;
import com.perfulandia.cl.perfulandia.model.Producto;
import com.perfulandia.cl.perfulandia.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> listar() {
        List<EntityModel<Producto>> productos = productoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> obtener(@PathVariable Integer id) {
        Producto producto = productoService.obtenerPorId(id);
        return assembler.toModel(producto);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> crear(@RequestBody Producto producto) {
        Producto newProducto = productoService.guardar(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).obtener(newProducto.getId())).toUri())
                .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Producto>> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        producto.setId(id);
        Producto updatedProducto = productoService.guardar(producto);
        return ResponseEntity.ok(assembler.toModel(updatedProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

