package com.perfulandia.perfulandia.controller;

import com.perfulandia.perfulandia.model.usuario;
import com.perfulandia.perfulandia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<usuario>> Listar() {
        List<usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }
    @GetMapping("/{id}")
    public ResponseEntity<usuario> buscar(@PathVariable Integer id, @RequestBody usuario usuario){
        try {
            usuario usr = usuarioService.findById(id);
            usr.setId(id);
            usr.setRun(usuario.getRun());
            usr.setNombres(usuario.getApellidos());
            usr.setFechaNacimiento(usuario.getFechaNacimiento());
            usr.setCorreo(usuario.getCorreo());

            usuarioService.save(usr);
            return ResponseEntity.ok(usuario);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
