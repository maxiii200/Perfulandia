package com.perfulandia.cl.perfulandia.controller;

import com.perfulandia.cl.perfulandia.assembler.VentaModelAssembler;
import com.perfulandia.cl.perfulandia.model.Producto;
import com.perfulandia.cl.perfulandia.model.Venta;
import com.perfulandia.cl.perfulandia.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/ventas")
public class VentaControllerV2 {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Venta>> listar() {
        List<EntityModel<Venta>> ventas = ventaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Venta> obtener(@PathVariable Integer id) {
        Venta venta = ventaService.obtenerPorId(id);
        return assembler.toModel(venta);
    }

    // Ejemplo método para obtener productos de una venta (si quieres tenerlo)
    @GetMapping(value = "/{id}/productos", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Producto>> obtenerProductos(@PathVariable Integer id) {
        Venta venta = ventaService.obtenerPorId(id);
        List<EntityModel<Producto>> productos = venta.getDetalles().stream()
                .map(detalle -> EntityModel.of(detalle.getProducto(),
                        linkTo(methodOn(VentaControllerV2.class).obtenerProducto(id, detalle.getProducto().getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(VentaControllerV2.class).obtenerProductos(id)).withSelfRel());
    }

    // Ejemplo método para obtener un producto específico de una venta
    @GetMapping(value = "/{ventaId}/productos/{productoId}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Producto> obtenerProducto(@PathVariable Integer ventaId, @PathVariable Integer productoId) {
        Venta venta = ventaService.obtenerPorId(ventaId);
        Producto producto = venta.getDetalles().stream()
                .map(detalle -> detalle.getProducto())
                .filter(p -> p.getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en esta venta"));
        return EntityModel.of(producto,
                linkTo(methodOn(VentaControllerV2.class).obtenerProducto(ventaId, productoId)).withSelfRel());
    }

    // POST, PUT, DELETE puedes agregarlos de manera similar si quieres

}

