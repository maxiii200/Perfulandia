package com.perfulandia.perfulandia.service;

import com.perfulandia.perfulandia.model.Venta;
import com.perfulandia.perfulandia.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {
    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository){
        this.ventaRepository= ventaRepository;
    }

    public List<Venta> obtenerTodas(){
        return ventaRepository.findAll();
    }

    public Venta guardar(Venta venta){
        return ventaRepository.save(venta);
    }
    public Venta obtenerPorId(Long id){
        return ventaRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id){
        ventaRepository.deleteById(id);
    }
}

