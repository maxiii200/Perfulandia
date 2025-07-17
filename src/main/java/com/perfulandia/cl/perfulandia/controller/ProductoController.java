package com.perfulandia.cl.perfulandia.controller;

import com.perfulandia.cl.perfulandia.model.Producto;
import com.perfulandia.cl.perfulandia.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar productos", description = "Obtiene una lista de todos los productos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos listados correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "204", description = "No hay productos registrados")
    })
    public List<Producto> listar() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Retorna la información de un producto específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public Producto obtener(
            @Parameter(description = "ID del producto a buscar", required = true)
            @PathVariable Integer id
    ) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Crea un nuevo producto en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producto.class),
                            examples = @ExampleObject(name = "EjemploProducto", value = "{ \"nombre\": \"Mouse Logitech\", \"descripcion\": \"Mouse inalámbrico\", \"precio\": 14990, \"stock\": 50 }")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public Producto crear(
            @RequestBody(description = "Producto a crear", required = true,
                    content = @Content(
                            schema = @Schema(implementation = Producto.class),
                            examples = @ExampleObject(value = "{ \"nombre\": \"Mouse Logitech\", \"descripcion\": \"Mouse inalámbrico\", \"precio\": 14990, \"stock\": 50 }")
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody Producto producto
    ) {
        return service.guardar(producto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto existente según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public void eliminar(
            @Parameter(description = "ID del producto a eliminar", required = true)
            @PathVariable Integer id
    ) {
        service.eliminar(id);
    }
}
