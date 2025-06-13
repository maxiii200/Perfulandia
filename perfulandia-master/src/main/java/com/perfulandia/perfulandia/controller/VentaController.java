package com.perfulandia.perfulandia.controller;



import com.perfulandia.perfulandia.model.Venta;
import com.perfulandia.perfulandia.service.VentaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public List<Venta> listar(){
        return ventaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Venta obtener(@PathVariable Long id){
        return ventaService.obtenerPorId(id);
    }
    @PostMapping
    public Venta crear(@RequestBody Venta venta){
        return ventaService.guardar(venta);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        ventaService.eliminar(id);
    }
}
