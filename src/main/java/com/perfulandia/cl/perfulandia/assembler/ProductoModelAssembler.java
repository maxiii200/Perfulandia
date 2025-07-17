package com.perfulandia.cl.perfulandia.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandia.cl.perfulandia.controller.ProductoControllerV2;
import com.perfulandia.cl.perfulandia.model.Producto;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).obtener(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).listar()).withRel("productos"));
    }
}
