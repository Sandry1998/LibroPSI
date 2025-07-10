package com.ceatformacion.libropsi.services;

import com.ceatformacion.libropsi.modell.Usuario;
import com.ceatformacion.libropsi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    public Optional<Usuario> buscarPorUsername(String username) {
        return repo.findByUsername(username);
    }

    public void guardarUsuario(Usuario usuario) {
        repo.save(usuario);
    }
}










