package com.perfulandia.cl.perfulandia;

import com.perfulandia.cl.perfulandia.model.Usuario;
import com.perfulandia.cl.perfulandia.repository.UsuarioRepository;
import com.perfulandia.cl.perfulandia.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(List.of(
                new Usuario(1, "21755585-k", "Maximiliano", "Alvarado", new Date(), "maxi@mail.com")
        ));

        List<Usuario> usuarios = usuarioService.findAll();

        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Usuario usuario = new Usuario(1, "21755585-k", "Maximiliano", "Alvarado", new Date(), "maxi@mail.com");


        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario found = usuarioService.findById(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario(1, "21601311", "Brayan", "Vilches", new Date(), "brayan@gmail.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario saved = usuarioService.save(usuario);

        assertNotNull(saved);
        assertEquals("Maxi", saved.getNombres());
    }

    @Test
    public void testDeleteById() {
        Integer id = 1;

        doNothing().when(usuarioRepository).deleteById(id);

        usuarioService.delete(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}

