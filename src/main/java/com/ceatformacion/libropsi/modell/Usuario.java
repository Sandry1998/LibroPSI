package com.ceatformacion.libropsi.modell;

import jakarta.persistence.*;

import java.util.List;


@Entity
    public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id_usuario;

        private String username;
        private String password;
        private String rol;

   /* @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Libro> libros;*/

    // Getters y setters

        public int getId_usuario() {
            return id_usuario;
        }

        public void setId_usuario(int id_usuario) {
            this.id_usuario = id_usuario;
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

       /* public List<Libro> getLibros() {
            return libros;
        }

        public void setLibros(List<Libro> libros) {
            this.libros = libros;
        }*/

        @Override
        public String toString() {
            return "Usuario{" +
                    "id_usuario=" + id_usuario +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", rol='" + rol + '\'' +
                    /*", libros=" + libros +*/
                    '}';
        }
    }


