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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registrar usuario con cifrado de contraseña y rol por defecto
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("ROLE_USER");
        }
        return usuarioRepository.save(usuario);
    }

    // Buscar usuario por username
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }

        // Guardar o actualizar usuario
        public Usuario guardarUsuario (Usuario usuario){
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            return usuarioRepository.save(usuario);
        }

        // Verificar si un usuario existe
        public boolean existeUsuario (String username){
            return usuarioRepository.findByUsername(username).isPresent();
        }
    }










