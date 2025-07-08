package com.ceatformacion.libropsi.services;

import com.ceatformacion.libropsi.modell.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioDetails implements UserDetails {

    private Usuario usuario;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Agrega el rol con el prefijo ROLE_
        return List.of(new SimpleGrantedAuthority(usuario.getRol()));
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cambiar si implementas expiración
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambiar si implementas bloqueo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cambiar si implementas expiración de credenciales
    }

    @Override
    public boolean isEnabled() {
        return true; // Cambiar si implementas habilitación/deshabilitación
    }
}
