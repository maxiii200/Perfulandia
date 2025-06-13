package com.perfulandia.perfulandia.service;

import com.perfulandia.perfulandia.model.usuario;
import com.perfulandia.perfulandia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public usuario findById(long id){
        return usuarioRepository.findById(id).get();
    }

    public usuario save(usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id){
        usuarioRepository.deleteById(id);
    }
}
