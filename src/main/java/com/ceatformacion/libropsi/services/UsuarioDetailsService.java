package com.ceatformacion.libropsi.services;


import com.ceatformacion.libropsi.modell.Usuario;
import com.ceatformacion.libropsi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

   private final UsuarioRepository usuarioRepository;

   @Autowired
    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

       System.out.println("Usuario no encontrado: " + usuario.getUsername() + ", Rol " + usuario.getRol());

       return new UsuarioDetails(usuario);
    }


}
