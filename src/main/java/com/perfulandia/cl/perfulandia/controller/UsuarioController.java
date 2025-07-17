package com.perfulandia.cl.perfulandia.controller;

import com.perfulandia.cl.perfulandia.model.Usuario;
import com.perfulandia.cl.perfulandia.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene una lista de todos los usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuario = usuarioService.findAll();
        if (usuario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @Operation(summary = "Guardar usuario", description = "Crea un nuevo usuario en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class),
                            examples = @ExampleObject(value = "{\"run\": \"12345678-9\", \"nombres\": \"Juan\", \"apellidos\": \"Pérez\", \"fechaNacimiento\": \"2000-01-01\", \"correo\": \"juan@correo.com\"}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Usuario> guardar(
            @RequestBody(description = "Usuario a crear", required = true,
                    content = @Content(
                            schema = @Schema(implementation = Usuario.class),
                            examples = @ExampleObject(name = "NuevoUsuario", value = "{\"run\": \"12345678-9\", \"nombres\": \"Juan\", \"apellidos\": \"Pérez\", \"fechaNacimiento\": \"2000-01-01\", \"correo\": \"juan@correo.com\"}")
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody Usuario usuario
    ) {
        Usuario productoNuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario", description = "Busca un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> buscar(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Integer id
    ) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> actualizar(
            @Parameter(description = "ID del usuario a actualizar", required = true)
            @PathVariable Integer id,
            @RequestBody(description = "Datos del usuario actualizados", required = true,
                    content = @Content(
                            schema = @Schema(implementation = Usuario.class),
                            examples = @ExampleObject(value = "{\"run\": \"87654321-0\", \"nombres\": \"Ana\", \"apellidos\": \"García\", \"fechaNacimiento\": \"1998-05-10\", \"correo\": \"ana@correo.com\"}")
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody Usuario usuario
    ) {
        try {
            Usuario usr = usuarioService.findById(id);
            usr.setId(id);
            usr.setRun(usuario.getRun());
            usr.setNombres(usuario.getNombres());
            usr.setApellidos(usuario.getApellidos());
            usr.setFechaNacimiento(usuario.getFechaNacimiento());
            usr.setCorreo(usuario.getCorreo());

            usuarioService.save(usr);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true)
            @PathVariable Integer id
    ) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
