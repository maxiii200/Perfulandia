package com.perfulandia.cl.perfulandia;

import com.perfulandia.cl.perfulandia.model.Venta;
import com.perfulandia.cl.perfulandia.repository.VentaRepository;
import com.perfulandia.cl.perfulandia.service.VentaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {

    @MockitoBean
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    @Test
    public void testFindAll() {
        Venta venta = new Venta();
        venta.setId(1);
        venta.setFecha(LocalDate.now());

        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        List<Venta> ventas = ventaService.obtenerTodas();

        assertNotNull(ventas);
        assertEquals(1, ventas.size());
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Venta venta = new Venta();
        venta.setId(id);
        venta.setFecha(LocalDate.now());

        when(ventaRepository.findById(id)).thenReturn(Optional.of(venta));

        Venta found = ventaService.obtenerPorId(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {
        Venta venta = new Venta();
        venta.setId(1);
        venta.setFecha(LocalDate.now());

        when(ventaRepository.save(venta)).thenReturn(venta);

        Venta saved = ventaService.guardar(venta);

        assertNotNull(saved);
        assertEquals(venta.getId(), saved.getId());
    }

    @Test
    public void testDeleteById() {
        Integer id = 1;

        doNothing().when(ventaRepository).deleteById(id);

        ventaService.eliminar(id);

        verify(ventaRepository, times(1)).deleteById(id);
    }
}

