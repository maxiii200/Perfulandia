package com.perfulandia.cl.perfulandia.controller;

import com.perfulandia.cl.perfulandia.model.Venta;
import com.perfulandia.cl.perfulandia.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
@Tag(name = "Ventas", description = "Operaciones relacionadas con las ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venta")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable Integer id) {
        Venta venta = ventaService.obtenerPorId(id);
        return ResponseEntity.ok(venta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar venta")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable Integer id, @RequestBody Venta venta) {
        venta.setId(id);
        Venta actualizado = ventaService.guardar(venta);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar venta")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Integer id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar ventas")
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.obtenerTodas();
        return ResponseEntity.ok(ventas);
    }

    @PostMapping
    @Operation(summary = "Guardar venta")
    public ResponseEntity<Venta> guardarVenta(@RequestBody Venta venta) {
        Venta creado = ventaService.guardar(venta);
        return ResponseEntity.ok(creado);
    }
}


