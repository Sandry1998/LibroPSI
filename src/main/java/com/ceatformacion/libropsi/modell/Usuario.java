package com.ceatformacion.libropsi.entity;

import com.ceatformacion.libropsi.services.UsuarioDetails;
import jakarta.persistence.*;


@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String rol;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario-> " +
                "\nId=" + id +
                "\nUsername: " + username +
                "\nPassword: " + password +
                "\nRol: " + rol;
    }
}
