package com.perfulandia.cl.perfulandia;

import com.perfulandia.cl.perfulandia.model.Producto;
import com.perfulandia.cl.perfulandia.repository.ProductoRepository;
import com.perfulandia.cl.perfulandia.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockitoBean
    private ProductoRepository productoRepository;

    @Test
    public void testFindAll() {
        when(productoRepository.findAll()).thenReturn(List.of(new Producto(1, "Calvin Klien", "Perfume bueno", 15.0, 1)));

        List<Producto> productos = productoService.obtenerTodos();

        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindById() {
        int id = 1;
        Producto producto = new Producto(id, "Calvin Klein", "Perfume bueno", 15.0, 1);

        when(productoRepository.findById((id))).thenReturn(Optional.of(producto));

        Producto found = productoService.obtenerPorId((id));

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {
        Producto producto = new Producto(1, "Calvin Klein", "Perfume bueno", 15.0, 1);

        when(productoRepository.save(producto)).thenReturn(producto);

        Producto saved = productoService.guardar(producto);

        assertNotNull(saved);
        assertEquals("Manzana", saved.getNombre());
    }

    @Test
    public void testDeleteById() {
        int id = 1;

        doNothing().when(productoRepository).deleteById((id));

        productoService.eliminar((id));

        verify(productoRepository, times(1)).deleteById((id));
    }
}
