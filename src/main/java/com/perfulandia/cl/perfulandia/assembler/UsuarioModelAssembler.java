package com.perfulandia.cl.perfulandia.assembler;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandia.cl.perfulandia.controller.UsuarioControllerV2;
import com.perfulandia.cl.perfulandia.model.Usuario;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).obtener(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listar()).withRel("usuarios"));
    }
}

