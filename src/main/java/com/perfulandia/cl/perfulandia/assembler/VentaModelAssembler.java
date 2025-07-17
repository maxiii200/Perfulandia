package com.perfulandia.cl.perfulandia.assembler;

import com.perfulandia.cl.perfulandia.controller.VentaControllerV2;
import com.perfulandia.cl.perfulandia.model.DetalleVenta;
import com.perfulandia.cl.perfulandia.model.Producto;
import com.perfulandia.cl.perfulandia.model.Venta;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {

    @Override
    public EntityModel<Venta> toModel(Venta venta) {
        List<Producto> productos = venta.getDetalles()
                .stream()
                .map(DetalleVenta::getProducto)
                .collect(Collectors.toList());

        List<EntityModel<Producto>> productosLinks = productos.stream()
                .map(producto -> EntityModel.of(producto,
                        linkTo(methodOn(VentaControllerV2.class).obtenerProducto(venta.getId(), producto.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Producto>> productosCollection = CollectionModel.of(productosLinks);

        EntityModel<Venta> ventaModel = EntityModel.of(venta,
                linkTo(methodOn(VentaControllerV2.class).obtener(venta.getId())).withSelfRel(),
                linkTo(methodOn(VentaControllerV2.class).listar()).withRel("ventas"));

        Link productosLink = linkTo(methodOn(VentaControllerV2.class).obtenerProductos(venta.getId())).withRel("productos");
        ventaModel.add(productosLink);

        return ventaModel;
    }
}

